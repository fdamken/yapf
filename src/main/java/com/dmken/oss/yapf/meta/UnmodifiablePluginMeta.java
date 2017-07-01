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
package com.dmken.oss.yapf.meta;

import java.net.URL;
import java.util.Optional;

import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.PluginType;
import com.dmken.oss.yapf.Version;

/**
 * An unmodifiable {@link PluginMeta} delegating all read methods to a wrapped
 * plugin meta.
 *
 */
public class UnmodifiablePluginMeta extends AbstractPluginMeta {
    /**
     * The wrapped plugin meta.
     *
     */
    private final PluginMeta pluginMeta;

    /**
     * Constructor of UnmodifiablePluginMeta.
     *
     * @param pluginMeta
     *            The {@link #pluginMeta} to wrap.
     */
    public UnmodifiablePluginMeta(final PluginMeta pluginMeta) {
        if (pluginMeta == null) {
            throw new IllegalArgumentException("PluginMeta must not be null!");
        }

        this.pluginMeta = pluginMeta;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getLocation()
     */
    @Override
    public URL getLocation() {
        return this.pluginMeta.getLocation();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getName()
     */
    @Override
    public String getName() {
        return this.pluginMeta.getName();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return this.pluginMeta.getDisplayName();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getVersion()
     */
    @Override
    public Version getVersion() {
        return this.pluginMeta.getVersion();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getMain()
     */
    @Override
    public String getMain() {
        return this.pluginMeta.getMain();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getPluginType()
     */
    @Override
    public PluginType getPluginType() {
        return this.pluginMeta.getPluginType();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getDependencies()
     */
    @Override
    public String[] getDependencies() {
        return this.pluginMeta.getDependencies();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getOptionalDependencies()
     */
    @Override
    public String[] getOptionalDependencies() {
        return this.pluginMeta.getOptionalDependencies();
    }

    /**
     * ${@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginMeta#getAuthors()
     */
    @Override
    public Optional<String[]> getAuthors() {
        return this.pluginMeta.getAuthors();
    }
}
