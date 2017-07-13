/*-
 * #%L
 * Yet Another Plugin Framework
 * %%
 * Copyright (C) 2017 Fabian Damken
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.dmken.oss.yapf.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.dmken.oss.yapf.Plugin;
import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.PluginType;
import com.dmken.oss.yapf.Version;
import com.dmken.oss.yapf.meta.exception.MalformedPluginMetaException;
import com.dmken.oss.yapf.spring.MethodInvocationCountingApplicationEventListener;
import com.dmken.oss.yapf.spring.YapfTestConfiguration;

@SuppressWarnings("javadoc")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = YapfTestConfiguration.class)
public class PluginLoaderTestSpring {
    @Rule
    public TemporaryFolder dir = new TemporaryFolder();

    @Autowired
    private MethodInvocationCountingApplicationEventListener listener;

    private PluginLoader pluginLoader;

    @Before
    public void setUp() throws Exception {
        this.pluginLoader = new PluginLoader();
    }

    @Test
    public void testPluginMeta_minimal() throws Exception {
        final String name = "test-min";
        final String version = "1.0.0";
        final String main = "this.is.not.relevant";
        final Version expectedVersion = new Version(1, 0, 0);

        final PluginMeta meta = this.parsePluginMeta(attributes -> {
            attributes.putValue("name", name);
            attributes.putValue("version", version);
            attributes.putValue("main", main);
        });

        this.assertPluginMeta(meta, name, expectedVersion, main, null, null, null, null, PluginType.REGULAR);
    }

    @Test
    public void testPluginMeta_maximal() throws Exception {
        final String name = "test-max";
        final String version = "1.2.3";
        final String main = "this.is.still.not.relevant";
        final String displayName = "Test Maximal";
        final String dependencies = "dep1, dep2, dep3',still-dep-3''";
        final String optionalDependencies = "opdep1, opdep2";
        final String authors = "Fabian Damken, Max Mustermann";
        final Version expectedVersion = new Version(1, 2, 3);
        final String[] expectedDependencies = { "dep1", "dep2", "dep3,still-dep-3'" };
        final String[] expectedOptionalDependencies = { "opdep1", "opdep2" };
        final String[] expectedAuthors = { "Fabian Damken", "Max Mustermann" };

        final PluginMeta meta = this.parsePluginMeta(attributes -> {
            attributes.putValue("name", name);
            attributes.putValue("version", version);
            attributes.putValue("main", main);
            attributes.putValue("display-name", displayName);
            attributes.putValue("dependencies", dependencies);
            attributes.putValue("optional-dependencies", optionalDependencies);
            attributes.putValue("authors", authors);
        });

        this.assertPluginMeta(meta, name, expectedVersion, main, displayName, expectedDependencies, expectedOptionalDependencies,
                expectedAuthors, PluginType.REGULAR);
    }

    @Test
    public void testPluginLoading() throws Exception {
        final String className = "EmptyTestPlugin";

        final Path jarFile = this.createJarFile(attributes -> {
            attributes.putValue("name", "test");
            attributes.putValue("version", "1.0.0");
            attributes.putValue("main", "test." + className);
        } , className);

        final PluginMeta meta = this.pluginLoader.extractPluginMeta(jarFile);

        this.assertPluginMeta(meta, "test", new Version(1, 0, 0), "test." + className, null, null, null, null,
                PluginType.REGULAR);

        final Plugin plugin = this.pluginLoader.loadPlugin(meta);

        Assert.assertEquals(1, this.listener.getOnPrePluginLoadEventInvocations());
        Assert.assertEquals(1, this.listener.getOnPostPluginLoadEventInvocations());

        Assert.assertTrue((boolean) plugin.getClass().getMethod("isTestSuccessful").invoke(plugin));
    }

    @Test
    public void testClassLoadingSeparation() throws Exception {
        final String classNameSpec = "SimpleSpecPlugin";
        final String classNameRegular = "SimpleRegularPlugin";
        final String classNameImpl = "SimpleImplPlugin";

        final Path jarFileSpec = this.createJarFile(attributes -> {
            attributes.putValue("name", "test-spec");
            attributes.putValue("version", "1.0.0");
            attributes.putValue("main", "test." + classNameSpec);
        } , classNameSpec);
        final Path jarFileRegular = this.createJarFile(attributes -> {
            attributes.putValue("name", "test");
            attributes.putValue("version", "1.0.0");
            attributes.putValue("main", "test." + classNameRegular);
        } , classNameRegular);
        final Path jarFileImpl = this.createJarFile(attributes -> {
            attributes.putValue("name", "test-impl");
            attributes.putValue("version", "1.0.0");
            attributes.putValue("main", "test." + classNameImpl);
        } , classNameImpl);

        final PluginMeta metaSpec = this.pluginLoader.extractPluginMeta(jarFileSpec);
        final PluginMeta metaRegular = this.pluginLoader.extractPluginMeta(jarFileRegular);
        final PluginMeta metaImpl = this.pluginLoader.extractPluginMeta(jarFileImpl);

        this.assertPluginMeta(metaSpec, "test", new Version(1, 0, 0), "test." + classNameSpec, null, null, null, null,
                PluginType.SPECIFICATION);
        this.assertPluginMeta(metaRegular, "test", new Version(1, 0, 0), "test." + classNameRegular, null, null, null, null,
                PluginType.REGULAR);
        this.assertPluginMeta(metaImpl, "test", new Version(1, 0, 0), "test." + classNameImpl, null, null, null, null,
                PluginType.IMPLEMENTATION);

        final Plugin pluginSpec = this.pluginLoader.loadPlugin(metaSpec);
        final Plugin pluginRegular = this.pluginLoader.loadPlugin(metaRegular);
        final Plugin pluginImpl = this.pluginLoader.loadPlugin(metaImpl);

        Assert.assertEquals(3, this.listener.getOnPrePluginLoadEventInvocations());
        Assert.assertEquals(3, this.listener.getOnPostPluginLoadEventInvocations());

        Assert.assertEquals(0xCAFE_BABE, pluginSpec.getClass().getMethod("testSpec").invoke(pluginSpec));
        Assert.assertEquals(42, pluginRegular.getClass().getMethod("testRegular").invoke(pluginRegular));
        Assert.assertEquals(0xDEAD_BEEF, pluginImpl.getClass().getMethod("testImpl").invoke(pluginImpl));
    }

    private PluginMeta parsePluginMeta(final Consumer<Attributes> attributeSetter)
            throws IOException, MalformedPluginMetaException {
        final Path file = this.dir.newFile().toPath();

        final Attributes attributes = new Attributes();
        attributeSetter.accept(attributes);
        final Manifest manifest = new Manifest();
        manifest.getEntries().put("yapf", attributes);
        final JarOutputStream stream = new JarOutputStream(Files.newOutputStream(file), manifest);
        stream.close();

        return this.pluginLoader.extractPluginMeta(file);
    }

    private void assertPluginMeta(final PluginMeta meta, final String expectedName, final Version expectedVersion,
            final String expectedMain, final String expectedDisplayName, final String[] expectedDependencies,
            final String[] expectedOptionalDependencies, final String[] expectedAuthors, final PluginType pluginType) {
        Assert.assertEquals(expectedName, meta.getName());
        Assert.assertEquals(expectedVersion, meta.getVersion());
        Assert.assertEquals(expectedMain, meta.getMain());
        Assert.assertEquals(expectedDisplayName == null ? expectedName : expectedDisplayName, meta.getDisplayName());
        if (expectedDependencies == null) {
            Assert.assertArrayEquals(new String[0], meta.getDependencies());
        } else {
            Assert.assertArrayEquals(expectedDependencies, meta.getDependencies());
        }
        if (expectedOptionalDependencies == null) {
            Assert.assertArrayEquals(new String[0], meta.getOptionalDependencies());
        } else {
            Assert.assertArrayEquals(expectedOptionalDependencies, meta.getOptionalDependencies());
        }
        if (expectedAuthors == null) {
            Assert.assertFalse(meta.getAuthors().isPresent());
        } else {
            Assert.assertTrue(meta.getAuthors().isPresent());
            Assert.assertArrayEquals(expectedAuthors, meta.getAuthors().get());
        }
        Assert.assertEquals(pluginType, meta.getPluginType());
    }

    private Path createJarFile(final Consumer<Attributes> attributeSetter, final String className) throws IOException {
        final Path workDir = this.dir.newFolder().toPath();
        final Path pluginClass = workDir.resolve(className + ".java");

        Files.copy(PluginLoaderTestSpring.class.getResourceAsStream("/test/" + className + ".java"), pluginClass,
                StandardCopyOption.REPLACE_EXISTING);

        // Build options.
        final List<String> options = new ArrayList<>();
        options.add("-classpath");
        final StringBuilder classpath = new StringBuilder();
        for (final URL url : ((URLClassLoader) PluginLoaderTestSpring.class.getClassLoader()).getURLs()) {
            classpath.append(url.getFile()).append(File.pathSeparator);
        }
        classpath.setLength(classpath.length() - 1);
        options.add(classpath.toString());
        // Prepare Java compiler.
        final JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        final StandardJavaFileManager fileManager = javac.getStandardFileManager(null, null, null);
        final Iterable<? extends JavaFileObject> javaFileObjects = fileManager
                .getJavaFileObjectsFromStrings(Arrays.asList(pluginClass.toAbsolutePath().toString()));
        // Compile!
        javac.getTask(null, null, null, options, null, javaFileObjects).call();
        // Cleanup.
        fileManager.close();

        final Path jarFile = workDir.resolve("plugin.jar");

        // Prepare and create JAR file including manifest.
        final Attributes attributes = new Attributes();
        attributeSetter.accept(attributes);
        final Manifest manifest = new Manifest();
        manifest.getEntries().put("yapf", attributes);
        final JarOutputStream stream = new JarOutputStream(Files.newOutputStream(jarFile), manifest);
        final JarEntry entry = new JarEntry("test/" + className + ".class");
        stream.putNextEntry(entry);
        Files.copy(workDir.resolve(className + ".class"), stream);
        stream.closeEntry();
        stream.close();

        return jarFile;
    }
}
