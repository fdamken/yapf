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

import java.util.Optional;

import jdk.nashorn.internal.runtime.Version;

/**
 * The plugin meta is the base of every plugin as it defines its name and its
 * main class (the class implementing the plugin interface).
 *
 */
public interface PluginMeta {
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
}
