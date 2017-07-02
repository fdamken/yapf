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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmken.oss.yapf.Plugin;
import com.dmken.oss.yapf.PluginConfig;
import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.PluginType;
import com.dmken.oss.yapf.Version;
import com.dmken.oss.yapf.config.ManifestPluginConfig;
import com.dmken.oss.yapf.meta.SimplePluginMeta;
import com.dmken.oss.yapf.meta.UnmodifiablePluginMeta;
import com.dmken.oss.yapf.meta.exception.MalformedPluginMetaException;
import com.dmken.oss.yapf.util.FileUtil;

/**
 * The {@link PluginLoader plugin loader} is a service for loading plugins. That
 * is managing the class loaders, work with the class loaders and so on.
 *
 */
public class PluginLoader {
    /**
     * The logger.
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginLoader.class);

    /**
     * The name of the attributes in the manifest.
     *
     */
    private static final String MANIFEST_NAME = "yapf";
    // Plugin-meta keys.
    // Required.
    /**
     * The key for {@link PluginMeta#getName()}.
     *
     */
    private static final String META_NAME = "name";
    /**
     * The key for {@link PluginMeta#getVersion()}.
     *
     */
    private static final String META_VERSION = "version";
    /**
     * The key for {@link PluginMeta#getMain()}.
     *
     */
    private static final String META_MAIN = "main";
    // Optional.
    /**
     * The key for {@link PluginMeta#getDisplayName()}.
     *
     */
    private static final String META_DISPLAY_NAME = "display-name";
    /**
     * The key for {@link PluginMeta#getDependencies()}.
     *
     */
    private static final String META_DEPENDENCIES = "dependencies";
    /**
     * The key for {@link PluginMeta#getOptionalDependencies()}.
     *
     */
    private static final String META_OPTIONAL_DEPENDENCIES = "optional-dependencies";
    /**
     * The key for {@link PluginMeta#getAuthors()}.
     *
     */
    private static final String META_AUTHORS = "authors";

    /**
     * The parent class loader delegating class loading to the actual plugin
     * class loaders.
     * 
     */
    private final DelegatingPluginClassLoader parentClassLoader = new DelegatingPluginClassLoader(Plugin.class.getClassLoader());

    /**
     * Extracts the {@link PluginMeta} from the {@link Manifest JAR manifest} or
     * the given file.
     *
     * @param file
     *            The JAR file to extract the plugin meta from.
     * @return The extracted {@link PluginMeta}.
     * @throws IOException
     *             If any I/O error occurs.
     * @throws MalformedPluginMetaException
     *             If the metadata is invalid (e.g. lacking of required
     *             attributes).
     */
    public PluginMeta extractPluginMeta(final Path file) throws IOException, MalformedPluginMetaException {
        if (!FileUtil.isJarFile(file)) {
            throw new IllegalArgumentException("File must be a JAR file!");
        }

        try (final JarFile jar = new JarFile(file.toFile())) {
            final PluginConfig metaConfig = new ManifestPluginConfig(jar.getManifest(), PluginLoader.MANIFEST_NAME);

            // Required properties.
            final String rawName = metaConfig.getString(PluginLoader.META_NAME);
            final String rawVersion = metaConfig.getString(PluginLoader.META_VERSION);
            final String main = metaConfig.getString(PluginLoader.META_MAIN);

            if (rawName == null) {
                throw new MalformedPluginMetaException(String.valueOf(file), "Missing name attribute!");
            }
            if (rawVersion == null) {
                throw new MalformedPluginMetaException(String.valueOf(file), "Missing version attributes!");
            }
            if (main == null) {
                throw new MalformedPluginMetaException(String.valueOf(file), "Missing main class attributes!");
            }

            // Parsed required properties.
            final PluginType pluginType = PluginType.parsePluginName(rawName);
            final String name = pluginType.extractPluginName(rawName);
            final Version version = new Version(rawVersion);

            // Optional properties.
            final String displayName = metaConfig.getString(PluginLoader.META_DISPLAY_NAME, name);
            final String[] dependencies = metaConfig.getStrings(PluginLoader.META_DEPENDENCIES);
            final String[] optionalDependencies = metaConfig.getStrings(PluginLoader.META_OPTIONAL_DEPENDENCIES);
            final String[] authors = metaConfig.getStrings(PluginLoader.META_AUTHORS, (String[]) null);

            final SimplePluginMeta pluginMeta = new SimplePluginMeta();
            pluginMeta.setLocation(file.toUri().toURL());
            pluginMeta.setName(name);
            pluginMeta.setVersion(version);
            pluginMeta.setMain(main);
            pluginMeta.setDisplayName(displayName);
            pluginMeta.setDependencies(dependencies);
            pluginMeta.setOptionalDependencies(optionalDependencies);
            pluginMeta.setAuthors(authors);
            pluginMeta.setPluginType(pluginType);
            return new UnmodifiablePluginMeta(pluginMeta);
        }
    }

    /**
     * Loads the plugin described by the given {@link PluginMeta metadata}.
     *
     * <p>
     * <b> NOTE: This does the plugin loading only! It does not create API
     * proxies, enable the plugin or similar. </b>
     * </p>
     *
     * <p>
     * <b> NOTE: This invokes {@link Plugin#onLoad()}. </b>
     * </p>
     *
     * @param meta
     *            The {@link PluginMeta metadata} about the plugin to load.
     * @return The loaded plugin.
     * @throws MalformedPluginMetaException
     *             If the plugin meta is malformed (including if the main class
     *             does not exist, is an interface, etc.).
     */
    // Class loader is not closed.
    @SuppressWarnings("resource")
    public Plugin loadPlugin(final PluginMeta meta) throws MalformedPluginMetaException {
        if (!PluginMeta.isValid(meta)) {
            throw new IllegalArgumentException("Meta must be valid!");
        }

        final URL location = meta.getLocation();
        final String name = meta.getName();
        final String displayName = meta.getDisplayName();
        final String main = meta.getMain();

        PluginLoader.LOGGER.debug("Starting loading of <{}>.", name);

        final PluginClassLoader classLoader = new PluginClassLoader(location, this.parentClassLoader);
        final Class<?> clazz;
        if (Math.abs(-1) == -1) {
            try {
                classLoader.close();
            } catch (final IOException ex) {
                assert false;
            }
        }
        clazz = classLoader.findClass(main, false)
                .orElseThrow(() -> new MalformedPluginMetaException(name, "Main class does not exist!"));
        if (!Plugin.class.isAssignableFrom(clazz)) {
            throw new MalformedPluginMetaException(name, "Main class is not a subclass of Plugin!");
        }
        final Class<? extends Plugin> pluginClass = clazz.asSubclass(Plugin.class);
        if (pluginClass.isInterface() || Modifier.isAbstract(pluginClass.getModifiers())) {
            throw new MalformedPluginMetaException(name, "Main class must be a regular class (not an interface or abstract)!");
        }
        final Constructor<? extends Plugin> ctor;
        try {
            ctor = pluginClass.getConstructor();
        } catch (final NoSuchMethodException cause) {
            throw new MalformedPluginMetaException(name, "Main class does not have a default constructor!", cause);
        }
        if (!Modifier.isPublic(ctor.getModifiers())) {
            throw new MalformedPluginMetaException(name, "Constructor of main class is not visible!");
        }
        if (!ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
        final Plugin plugin;
        try {
            plugin = ctor.newInstance();
        } catch (final InstantiationException cause) {
            throw new MalformedPluginMetaException(name, "Failed to create instance of plugin!", cause);
        } catch (final IllegalAccessException cause) {
            throw new MalformedPluginMetaException(name, "Failed to access constructor of main class!", cause);
        } catch (final InvocationTargetException cause) {
            throw new MalformedPluginMetaException(name, "Constructor of main class threw an exception!", cause);
        }

        if (meta.getPluginType() == PluginType.SPECIFICATION) {
            // Only add class loader to share classes if plugin is a
            // specification and providing model classes.
            this.parentClassLoader.addClassLoader(name, classLoader);
        }

        PluginLoader.LOGGER.info("Loaded {} plugin {}.", meta.getPluginType(), displayName);

        // Tell plugin that it is loaded.
        plugin.onLoad();

        return plugin;
    }
}
