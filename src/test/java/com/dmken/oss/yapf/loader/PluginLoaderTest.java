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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.Version;

@SuppressWarnings("javadoc")
public class PluginLoaderTest {
    @Rule
    public TemporaryFolder dir = new TemporaryFolder();

    private PluginLoader pluginLoader;
    private Path file;

    @Before
    public void setUp() throws Exception {
        this.pluginLoader = new PluginLoader();
        this.file = this.dir.newFile().toPath();
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

        this.assertPluginMeta(meta, name, expectedVersion, main, null, null, null, null);
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
                expectedAuthors);
    }

    private PluginMeta parsePluginMeta(final Consumer<Attributes> attributeSetter) throws IOException {
        final Attributes attributes = new Attributes();
        attributeSetter.accept(attributes);
        final Manifest manifest = new Manifest();
        manifest.getEntries().put("yapf", attributes);
        final JarOutputStream stream = new JarOutputStream(Files.newOutputStream(this.file), manifest);
        stream.close();

        return this.pluginLoader.extractPluginMeta(this.file);
    }

    private void assertPluginMeta(final PluginMeta meta, final String expectedName, final Version expectedVersion,
            final String expectedMain, final String expectedDisplayName, final String[] expectedDependencies,
            final String[] expectedOptionalDependencies, final String[] expectedAuthors) {
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
    }
}
