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

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.dmken.oss.yapf.PluginConfig;

/**
 * An abstract implementation of {@link PluginConfig} for generalization.
 *
 */
public abstract class AbstractPluginConfig implements PluginConfig {
    /**
     * The default values defined using {@link #defineDefault(String, String)}
     * or {@link #defineDefaults(String, String...)}.
     * 
     */
    private final Map<String, Object> defaults = new HashMap<>();

    /**
     * The path where to save the configuration.
     * 
     */
    private final Path file;

    /**
     * The comment.
     * 
     */
    protected String comment;

    /**
     * Constructor of AbstractPluginConfig.
     *
     * @param file
     *            See {@link #file}.
     */
    public AbstractPluginConfig(final Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }

        this.file = file;
    }

    /**
     * <b>Internal usage only!</b>
     * 
     * <p>
     * Gets the {@link String} value of the given property.
     * </p>
     *
     * @param property
     *            The name of the property.
     * @return The property value, if any. Otherwise <code>null</code>.
     */
    protected abstract String getString0(final String property);

    /**
     * <b>Internal usage only!</b>
     * 
     * <p>
     * Gets the {@link String String[]} value of the given property.
     * </p>
     *
     * @param property
     *            The name of the property.
     * @return The property value, if any. Otherwise <code>null</code>.
     */
    protected String[] getStrings0(final String property) {
        return PluginConfig.super.getStrings(property, (String[]) null);
    }

    /**
     * <b>Internal usage only!</b>
     * 
     * <p>
     * Sets the value of the given property.
     * </p>
     *
     * @param property
     *            The name of the property.
     * @param value
     *            The new property value.
     */
    protected abstract void setString0(final String property, final String value);

    /**
     * <b>Internal usage only!</b>
     * 
     * <p>
     * Sets the value of the given property.
     * </p>
     *
     * @param property
     *            The name of the property.
     * @param value
     *            The new property value.
     */
    protected void setStrings0(final String property, final String... value) {
        PluginConfig.super.setStrings(property, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#defineDefault(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void defineDefault(final String property, final String value) {
        this.defaults.put(property, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#defineDefaults(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public void defineDefaults(final String property, final String... value) {
        this.defaults.put(property, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#getString(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String getString(final String property, final String value) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property must not be null or empty!");
        }

        String result = this.getString0(property);
        if (result == null) {
            final Object def = this.defaults.get(property);
            if (def instanceof String) {
                result = (String) def;
            }
        }
        if (result == null) {
            result = value;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#getStrings(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String[] getStrings(final String property, final String... value) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property must not be null or empty!");
        }

        String[] result = this.getStrings0(property);
        if (result == null) {
            final Object def = this.defaults.get(property);
            if (def instanceof String[]) {
                result = (String[]) def;
            }
        }
        if (result == null) {
            result = value;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#setString(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void setString(final String property, final String value) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property must not be null or empty!");
        }

        this.setString0(property, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#setStrings(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public void setStrings(final String property, final String... value) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property must not be null or empty!");
        }

        this.setStrings0(property, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.dmken.oss.yapf.PluginConfig#setComment(java.lang.String)
     */
    @Override
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return {@link #file}.
     */
    protected Path getFile() {
        return this.file;
    }
}
