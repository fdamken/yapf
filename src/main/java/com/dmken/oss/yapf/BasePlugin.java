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
 * Base implementation of {@link Plugin} that should be extended in order to
 * implement a plugin.
 * 
 * <p>
 * <b> Do not implement {@link Plugin} directly! </b>
 * </p>
 *
 */
public class BasePlugin implements Plugin {
    /**
     * The plugin manager that has loaded this plugin.
     * 
     */
    private PluginManager pluginManager;
    /**
     * Meta data about this plugin.
     * 
     */
    private PluginMeta pluginMeta;
    /**
     * The data folder where the configuration lives and the plugin can store
     * data.
     * 
     */
    private Path dataFolder;
    /**
     * The general plugin configuration (lives in {@link #dataFolder}).
     * 
     */
    private PluginConfig config;

    /**
     * The enabled-state of this plugin.
     * 
     */
    private boolean enabled;

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#__do_not_implement_this_interface_directly()
     */
    @Deprecated
    @Override
    public final void __do_not_implement_this_interface_directly() {
        // Nothing to do.
    }

    /**
     * Checks whether this plugin is initialized correctly (that is that no
     * fields are <code>null</code>).
     * 
     * @throws IllegalStateException
     *             If the plugin is not initialized correctly.
     */
    public void checkInitState() {
        if (this.pluginManager == null || this.pluginMeta == null || this.dataFolder == null || this.config == null) {
            throw new IllegalStateException("PLugin is not yet initialized!");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#onUnload()
     */
    @Override
    public void onUnload() {
        // May be implemented by subclasses.
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#onDisable()
     */
    @Override
    public void onDisable() {
        // may be implemented by subclasses.
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#onEnable()
     */
    @Override
    public void onEnable() {
        // may be implemented by subclasses.
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#onLoad()
     */
    @Override
    public void onLoad() {
        // may be implemented by subclasses.
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#getDataFolder()
     */
    @Override
    public Path getDataFolder() {
        return this.dataFolder;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#getConfig()
     */
    @Override
    public PluginConfig getConfig() {
        return this.config;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#getPluginMeta()
     */
    @Override
    public PluginMeta getPluginMeta() {
        return this.pluginMeta;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#getPluginManager()
     */
    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.Plugin#setEnabled(boolean)
     */
    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @param pluginManager
     *            the {@link #pluginManager} to set
     */
    public void setPluginManager(final PluginManager pluginManager) {
        if (this.pluginManager != null) {
            throw new IllegalStateException("Plugin manager is already initialized!");
        }

        this.pluginManager = pluginManager;
    }

    /**
     *
     * @param pluginMeta
     *            the {@link #pluginMeta} to set
     */
    public void setPluginMeta(final PluginMeta pluginMeta) {
        if (this.pluginMeta != null) {
            throw new IllegalStateException("Plugin meta is already initialized!");
        }

        this.pluginMeta = pluginMeta;
    }

    /**
     *
     * @param dataFolder
     *            the {@link #dataFolder} to set
     */
    public void setDataFolder(final Path dataFolder) {
        if (this.dataFolder != null) {
            throw new IllegalStateException("Data folder is already initialized!");
        }

        this.dataFolder = dataFolder;
    }

    /**
     *
     * @param config
     *            the {@link #config} to set
     */
    public void setConfig(final PluginConfig config) {
        if (this.config != null) {
            throw new IllegalStateException("Config is already initialized!");
        }

        this.config = config;
    }
}
