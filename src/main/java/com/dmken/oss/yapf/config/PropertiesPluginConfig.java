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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Properties;

import com.dmken.oss.yapf.PluginConfig;

/**
 * An implementation of {@link PluginConfig} using Java's standard
 * {@link Properties}.
 *
 */
public class PropertiesPluginConfig extends AbstractPluginConfig {
    /**
     * The properties.
     *
     */
    private final Properties properties = new Properties();

    /**
     * Constructor of PropertiesPluginConfig.
     *
     * @param file
     *            The path where to store the configuration.
     */
    public PropertiesPluginConfig(final Path file) {
        super(file);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#load(java.io.InputStream)
     */
    @Override
    public void load(final InputStream in) throws IOException {
        this.properties.load(in);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#load()
     */
    @Override
    public void load() throws IOException {
        try (final InputStream in = new BufferedInputStream(new FileInputStream(this.getFile().toFile()))) {
            this.load(in);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#save()
     */
    @Override
    public void save() throws IOException {
        try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(this.getFile().toFile()))) {
            this.properties.store(out, this.comment);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.config.AbstractPluginConfig#getString0(java.lang.String)
     */
    @Override
    protected String getString0(final String property) {
        return this.properties.getProperty(property);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.config.AbstractPluginConfig#setString0(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void setString0(final String property, final String value) {
        this.properties.setProperty(property, value);
    }
}
