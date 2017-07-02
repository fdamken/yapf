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

import java.nio.file.Path;

/**
 * This is the main interface of YAPF.
 * 
 * <p>
 * Every plugin has to implement this interface (or extend a subclass, which is
 * recommended) to become a real plugin. This class has to be referenced in the
 * {@link PluginMeta plugin metadata} file to be loaded correctly.
 * </p>
 * 
 * <p>
 * <b> NOTE: Do not implement this interface directly but extend
 * {@link BasePlugin}! </b>
 * </p>
 *
 * @see PluginManager
 * @see PluginMeta
 * @see PluginType
 */
public interface Plugin {
    /**
     * Method to remind the developer not to implement this interface directly
     * for those that do not read JavaDoc. Please extend {@link BasePlugin}
     * instead.
     *
     * @deprecated Extend {@link BasePlugin} instead.
     */
    @Deprecated
    void __do_not_implement_this_interface_directly();

    /**
     * Stub method. To be overridden.
     * 
     * <p>
     * This method is invoked right before the plugin gets unloaded.
     * </p>
     * 
     * <p>
     * <b> NOTE: This does not take care of dependencies! That is it is not
     * guaranteed that the dependencies are still loaded before this method is
     * invoked. </b>
     * </p>
     *
     */
    void onUnload();

    /**
     * Stub method. To be overridden.
     * 
     * <p>
     * This method is invoked right before the plugin gets disabled (also if
     * {@link #setEnabled(boolean)} was invoked <code>false</code>).
     * </p>
     *
     */
    void onDisable();

    /**
     * Stub method. To be overridden.
     * 
     * <p>
     * This method is invoked right after the plugin was enabled (also if
     * {@link #setEnabled(boolean)} was invoked with <code>true</code>).
     * </p>
     *
     */
    void onEnable();

    /**
     * Stub method. To be overridden.
     * 
     * <p>
     * This method is invoked right after the plugin was loaded.
     * </p>
     * 
     * <p>
     * <b> NOTE: This does not take care of dependencies! That is it is not
     * guaranteed that the dependencies are enabled before this method is
     * invoked. </b>
     * </p>
     *
     */
    void onLoad();

    /**
     * The path of the (existing) directory where the plugin can store files
     * (e.g. configuration files).
     * 
     * <p>
     * <b> NOTE: Files must not be stored anywhere else! </b>
     * </p>
     *
     * @return The path of the data directory.
     */
    Path getDataFolder();

    /**
     *
     * @return The base configuration of this plugin.
     */
    PluginConfig getConfig();

    /**
     *
     * @return The {@link PluginMeta metadata} about this plugin.
     */
    PluginMeta getPluginMeta();

    /**
     *
     * @return The {@link PluginManager plugin manager} that has loaded this
     *         plugin.
     */
    PluginManager getPluginManager();

    /**
     * Checks whether the plugin is enabled. If a plugin is not enabled, its
     * exposed API can not be used.
     *
     * @return Whether this plugin is enabled or not.
     */
    boolean isEnabled();

    /**
     * Sets this plugin as enabled or disabled. If a plugin is not enabled, its
     * exposed API can not be used.
     *
     * @param enabled
     *            Whether to enable or disable this plugin.
     */
    void setEnabled(final boolean enabled);
}
