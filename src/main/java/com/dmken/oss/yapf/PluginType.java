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

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Defines the different plugin types, which are:
 * <table>
 * <caption>Plugin Types</caption>
 * <tr>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Suffixes</th>
 * </tr>
 * <tr>
 * <td>Specification/API</td>
 * <td>An API plugin defines one or more services (thru interfaces) that are not
 * implemented. It may also contain model classes. The
 * classes/interfaces/enumerations in an API plugin are exposed to other plugins
 * and they are able to access them. For interfaces, the plugin manager has to
 * be consulted to get the actual implementation (or, if using Spring, they can
 * be autowired).</td>
 * <td><code>-api</code>, <code>-spec</code>, <code>-specification</code></td>
 * </tr>
 * <tr>
 * <td>Implementation</td>
 * <td>An implementation plugin implements the API of an API plugin which must
 * be loaded first. Its implementations have to be registered at the plugin
 * manager which then generates delegators from the exposed interfaces to the
 * actual implementations. This way it is possible to deploy, undeploy and
 * redeploy plugins at runtime.</td>
 * <td><code>-impl</code>, <code>-implementation</code></td>
 * </tr>
 * <tr>
 * <td>Regular</td>
 * <td>A regular plugin does neither define nor implement an API but is just
 * using APIs of other plugins. It must not expose any APIs.</td>
 * <td>Nothing, any name matching neither the API nor the implementation
 * suffixes is considered to be a regular plugin.</td>
 * </table>
 *
 * <p>
 * The plugin type is defined by the name of the plugin (thru the suffixes).
 * That is, a plugin with the name <code>XXXXX-api</code> defines an API and a
 * plugin with the name <code>XXXXX-impl</code> implements an API. <br>
 * The part before the suffix is considered to be the actual plugin name and it
 * is used for probing which implementation implements which API.
 * </p>
 *
 */
public enum PluginType {
    /**
     * Specification plugin.
     * 
     * @see PluginType
     */
    SPECIFICATION("-api", "-spec", "-specification"),
    /**
     * Implementation plugin.
     * 
     * @see PluginType
     */
    IMPLEMENTATION("-impl", "-implementation"),
    /**
     * Regular plugin.
     * 
     * @see PluginType
     */
    REGULAR;

    /**
     * The suffixes.
     * 
     * @see PluginType
     */
    private final String[] suffixes;

    /**
     * Constructor of PluginType.
     *
     * @param suffixes
     *            The {@link #suffixes}.
     */
    private PluginType(final String... suffixes) {
        this.suffixes = suffixes;
    }

    /**
     * Parses the given plugin name using the suffixes and gets the correct
     * plugin type. That is to let a plugin be an {@link #SPECIFICATION API},
     * its name must end with either <code>-api</code>, <code>-spec</code> or
     * <code>-specification</code>. The same goes for an implementation.
     *
     * @param pluginName
     *            The name of the plugin.
     * @return The plugin type. If the name does not end with one of the
     *         suffixes, the plugin is considered to be a {@link #REGULAR
     *         regular} plugin.
     */
    public static PluginType parsePluginName(final String pluginName) {
        if (pluginName == null) {
            throw new IllegalArgumentException("PluginName must not be null!");
        }

        for (final PluginType type : PluginType.values()) {
            for (final String suffix : type.getSuffixes()) {
                if (pluginName.endsWith(suffix)) {
                    return type;
                }
            }
        }
        return REGULAR;
    }

    /**
     * Extracts the actual plugin name without the suffix from the given plugin
     * name.
     *
     * @param pluginName
     *            The name of the plugin.
     * @return The actual plugin name without the suffix.
     */
    public String extractPluginName(final String pluginName) {
        if (pluginName == null) {
            throw new IllegalArgumentException("PluginName must not be null!");
        }

        String result = pluginName;
        for (final String suffix : this.suffixes) {
            result = result.replace(Pattern.quote(suffix) + "$", "");
        }
        return result;
    }

    /**
     *
     * @return {@link #suffixes}.
     */
    public String[] getSuffixes() {
        return Arrays.copyOf(this.suffixes, this.suffixes.length);
    }
}
