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
package com.dmken.oss.yapf.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.dmken.oss.yapf.PluginConfig;

/**
 * An implementation of {@link PluginConfig} using Java's {@link Manifest JAR
 * manifest}.
 *
 * <p>
 * <b> NOTE: This is a read-only configuration! </b>
 * </p>
 *
 */
public class ManifestPluginConfig extends AbstractPluginConfig {
    /**
     * The properties.
     *
     */
    private final Manifest manifest;
    /**
     * The root name of the manifest entries to start the properties from.
     *
     */
    private final String name;

    /**
     * Constructor of PropertiesPluginConfig.
     *
     * @param manifest
     *            The {@link #manifest} to set.
     * @param name
     *            The {@link #name} to set.
     */
    public ManifestPluginConfig(final Manifest manifest, final String name) {
        this.manifest = manifest;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#load(java.io.InputStream)
     */
    @Override
    public void load(final InputStream in) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#load()
     */
    @Override
    public void load() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#save()
     */
    @Override
    public void save() throws IOException {
        throw new UnsupportedOperationException("read only");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.config.AbstractPluginConfig#getString0(java.lang.String)
     */
    @Override
    protected String getString0(final String property) {
        if (this.manifest == null) {
            return null;
        }
        final Attributes attributes = this.manifest.getAttributes(this.name);
        if (attributes == null) {
            return null;
        }
        return attributes.getValue(property);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.config.AbstractPluginConfig#setString0(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void setString0(final String property, final String value) {
        throw new UnsupportedOperationException("read only");
    }
}
