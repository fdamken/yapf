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

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a special {@link URLClassLoader class loader} for loading plugins.
 * This includes class loading across multiple plugin class loaders.
 *
 */
public class PluginClassLoader extends URLClassLoader {
    /**
     * The logger.
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    /**
     * Cache for already loaded classes.
     *
     */
    private final Map<String, Class<?>> classCache = new WeakHashMap<>();

    /**
     * Constructor of PluginClassLoader.
     *
     * @param url
     *            The URL to load classes from.
     */
    public PluginClassLoader(final URL url) {
        super(new URL[] { url });

        PluginClassLoader.LOGGER.trace("Creating new PluginClassLoader for <{0}>.", url.toString());
    }

    /**
     * {@inheritDoc}
     *
     * @see java.net.URLClassLoader#findClass(java.lang.String)
     */
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        return this.findClass(name, true).orElseThrow(() -> new ClassNotFoundException(name));
    }

    /**
     * Finds the class with the given name and loads it. If <code>global</code>
     * is set to <code>true</code>, the class loaders of other plugins are
     * probed to load the class too. This is done for class usage across
     * plugins.
     *
     * @param name
     *            The full qualified name of the class to find.
     * @param global
     *            Whether to search globally for the class.
     * @return The found class.
     */
    private Optional<Class<?>> findClass(final String name, final boolean global) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty!");
        }

        if (global) {
            PluginClassLoader.LOGGER.debug("Searching globally for class <{0}>.", name);
        } else {
            PluginClassLoader.LOGGER.debug("Searching for class <{0}>.", name);
        }

        PluginClassLoader.LOGGER.trace("Probing cache for class <{0}>.", name);
        Class<?> result = this.classCache.get(name);
        if (result == null && global) {
            PluginClassLoader.LOGGER.trace("Not found! Probing global search <{0}>.", name);

            // TODO: Do global loading.
        }
        if (result == null) {
            PluginClassLoader.LOGGER.trace("Not found! Probing parent class loader <{0}>.", name);

            try {
                result = super.findClass(name);
            } catch (final ClassNotFoundException dummy) {
                // Class not found. Ignore.
            }
        }
        if (result == null) {
            PluginClassLoader.LOGGER.trace("Not found! Probing forName(...) with current class loader <{0}>.", name);

            try {
                result = Class.forName(name, true, this);
            } catch (final ClassNotFoundException dummy) {
                // Class not found. Ignore.
            }
        }
        if (result != null) {
            this.classCache.put(name, result);
        }

        if (result == null) {
            PluginClassLoader.LOGGER.debug("Class for found: {0}", name);
        } else {
            PluginClassLoader.LOGGER.debug("Class found: {0}", name);
        }

        return Optional.ofNullable(result);
    }
}
