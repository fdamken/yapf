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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple class loader delegating its load calls to the plugin-specific class
 * loaders. This might be reworked if proxies are implemented.
 *
 */
public class DelegatingPluginClassLoader extends ClassLoader {
    /**
     * The class loaders responsible for one plugin each (the key is the plugin
     * name).
     *
     */
    private final Map<String, PluginClassLoader> classLoaders = new HashMap<>();

    /**
     * Constructor of DelegatingPluginClassLoader.
     *
     * @param parent
     *            The parent class loader.
     */
    public DelegatingPluginClassLoader(final ClassLoader parent) {
        super(parent);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.ClassLoader#findClass(java.lang.String)
     */
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        for (final Iterator<PluginClassLoader> it = this.classLoaders.values().iterator(); clazz == null && it.hasNext();) {
            clazz = it.next().findClass(name, false).orElse(null);
        }
        if (clazz == null) {
            clazz = super.findClass(name);
        }
        return clazz;
    }

    /**
     * Adds the given plugin class loader responsible for the given plugin name
     * to the list of probable class loaders.
     *
     * @param pluginName
     *            The name of the plugin the class loader is responsible for.
     * @param classLoader
     *            The class loader.
     */
    void addClassLoader(final String pluginName, final PluginClassLoader classLoader) {
        this.classLoaders.put(pluginName, classLoader);
    }

    /**
     * Removes the class loader responsible for the given plugin.
     *
     * @param pluginName
     *            The name of the plugin to remove the class loader for.
     */
    void removeClassLoader(final String pluginName) {
        this.classLoaders.remove(pluginName);
    }
}
