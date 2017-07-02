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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
     * Class cache to prevent potential redefinitions of classes which causes a
     * {@link LinkageError}.
     * 
     */
    private final Map<String, Class<?>> classCache = new HashMap<>();

    /**
     * Constructor of PluginClassLoader.
     * 
     * @param url
     *            The URL to load classes from.
     * @param parent
     *            The parent class loader.
     */
    public PluginClassLoader(final URL url, final DelegatingPluginClassLoader parent) {
        super(new URL[] { url }, parent);

        PluginClassLoader.LOGGER.trace("Creating new PluginClassLoader for <{}>.", url.toString());
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
    Optional<Class<?>> findClass(final String name, final boolean global) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty!");
        }

        if (global) {
            PluginClassLoader.LOGGER.debug("Searching globally for class <{}>.", name);
        } else {
            PluginClassLoader.LOGGER.debug("Searching for class <{}>.", name);
        }

        PluginClassLoader.LOGGER.trace("Probing class cache <{}>.", name);
        Class<?> result = this.classCache.get(name);
        if (result == null) {
            PluginClassLoader.LOGGER.trace("Not found! Probing classpath <{}>.", name);
            try {
                result = super.findClass(name);
            } catch (final ClassNotFoundException dummy) {
                // Class not found. Ignore.
            }
        }
        if (global) {
            PluginClassLoader.LOGGER.trace("Not found! Probing parent class loader <{}>.", name);

            try {
                result = this.getCastedParent().findClass(name);
            } catch (final ClassNotFoundException dummy) {
                // Class not found. Ignore.
            }
        }
        if (result == null) {
            PluginClassLoader.LOGGER.trace("Not found! Probing forName(...) with current class loader <{}>.", name);

            try {
                result = Class.forName(name, false, this);
            } catch (final ClassNotFoundException dummy) {
                // Class not found. Ignore.
            }
        }

        if (result != null) {
            this.classCache.put(name, result);
        }

        if (result == null) {
            PluginClassLoader.LOGGER.debug("Class for found: {}", name);
        } else {
            PluginClassLoader.LOGGER.debug("Class found: {}", name);
        }

        return Optional.ofNullable(result);
    }

    /**
     * Casts the parent to {@link DelegatingPluginClassLoader} (which is save as
     * of the constructor).
     *
     * @return The {@link DelegatingPluginClassLoader}.
     */
    private DelegatingPluginClassLoader getCastedParent() {
        return (DelegatingPluginClassLoader) this.getParent();
    }
}
