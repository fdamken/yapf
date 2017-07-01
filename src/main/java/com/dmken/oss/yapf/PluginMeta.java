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
package com.dmken.oss.yapf;

import java.net.URL;
import java.util.Optional;

/**
 * The plugin meta is the base of every plugin as it defines its name and its
 * main class (the class implementing the plugin interface).
 *
 */
public interface PluginMeta {
    /**
     *
     * @return The location where the plugin lives (this is an URI pointing to a
     *         JAR file). It must be guaranteed that the file exists and is
     *         accessible.
     */
    URL getLocation();

    /**
     *
     * @return The name of the plugin (without the suffix).
     * @see PluginType
     */
    String getName();

    /**
     *
     * @return The display name of the plugin.
     */
    String getDisplayName();

    /**
     *
     * @return The version of the plugin.
     */
    Version getVersion();

    /**
     *
     * @return The full qualified name of the main class of the plugin.
     */
    String getMain();

    /**
     *
     * @return The type of the plugin.
     * @see PluginType
     */
    PluginType getPluginType();

    /**
     *
     * @return The dependencies of the plugin.
     */
    String[] getDependencies();

    /**
     *
     * @return The optional dependencies of the plugin.
     */
    String[] getOptionalDependencies();

    /**
     *
     * @return The authors of the plugin.
     */
    Optional<String[]> getAuthors();

    /**
     * Checks whether the given {@link PluginMeta meta} is valid (that is that
     * no methods return <code>null</code> references).
     *
     * @param meta
     *            The plugin meta. Can be <code>null</code>.
     * @return Whether the given meta is valid.
     */
    static boolean isValid(final PluginMeta meta) {
        return meta != null //
                && meta.getLocation() != null //
                && meta.getName() != null //
                && meta.getDisplayName() != null //
                && meta.getVersion() != null //
                && meta.getMain() != null //
                && meta.getPluginType() != null //
                && meta.getDependencies() != null //
                && meta.getOptionalDependencies() != null //
                && meta.getAuthors() != null;
    }
}
