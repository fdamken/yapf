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
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.dmken.oss.yapf.PluginConfig;
import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.PluginType;
import com.dmken.oss.yapf.Version;
import com.dmken.oss.yapf.config.ManifestPluginConfig;
import com.dmken.oss.yapf.meta.SimplePluginMeta;
import com.dmken.oss.yapf.meta.UnmodifiablePluginMeta;
import com.dmken.oss.yapf.util.FileUtil;

/**
 * The {@link PluginLoader plugin loader} is a service for loading plugins. That
 * is managing the class loaders, work with the class loaders and so on.
 *
 */
public class PluginLoader {
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
     * Extracts the {@link PluginMeta} from the {@link Manifest JAR manifest} or
     * the given file.
     *
     * @param file
     *            The JAR file to extract the plugin meta from.
     * @return The extracted {@link PluginMeta}.
     * @throws IOException
     *             If any I/O error occurs.
     */
    public PluginMeta extractPluginMeta(final Path file) throws IOException {
        if (!FileUtil.isJarFile(file)) {
            throw new IllegalArgumentException("File emust be a JAR file!");
        }

        try (final JarFile jar = new JarFile(file.toFile())) {
            final PluginConfig metaConfig = new ManifestPluginConfig(jar.getManifest(), PluginLoader.MANIFEST_NAME);

            final String rawName = metaConfig.getString(PluginLoader.META_NAME);
            final PluginType pluginType = PluginType.parsePluginName(rawName);
            final String name = pluginType.extractPluginName(rawName);
            final Version version = new Version(metaConfig.getString(PluginLoader.META_VERSION));
            final String main = metaConfig.getString(PluginLoader.META_MAIN);
            final String displayName = metaConfig.getString(PluginLoader.META_DISPLAY_NAME, name);
            final String[] dependencies = metaConfig.getStrings(PluginLoader.META_DEPENDENCIES);
            final String[] optionalDependencies = metaConfig.getStrings(PluginLoader.META_OPTIONAL_DEPENDENCIES);
            final String[] authors = metaConfig.getStrings(PluginLoader.META_AUTHORS, (String[]) null);

            final SimplePluginMeta pluginMeta = new SimplePluginMeta();
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
}
