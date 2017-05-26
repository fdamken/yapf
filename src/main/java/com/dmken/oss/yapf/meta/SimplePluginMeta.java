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

import java.util.Arrays;
import java.util.Optional;

import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.PluginType;
import com.dmken.oss.yapf.Version;

/**
 * A simple implementation of {@link PluginMeta} with Getters and Setters.
 *
 */
@SuppressWarnings("javadoc")
public class SimplePluginMeta extends AbstractPluginMeta {
    private String name;
    private String displayName;
    private Version version;
    private String main;
    private PluginType pluginType;
    private String[] dependencies;
    private String[] optionalDependencies;
    private String[] authors;

    // ~ Getters ~

    /**
     *
     * @return {@link #name}.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return {@link #displayName}.
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     *
     * @return {@link #version}.
     */
    @Override
    public Version getVersion() {
        return this.version;
    }

    /**
     *
     * @return {@link #main}.
     */
    @Override
    public String getMain() {
        return this.main;
    }

    /**
     *
     * @return {@link #pluginType}.
     */
    @Override
    public PluginType getPluginType() {
        return this.pluginType;
    }

    /**
     *
     * @return {@link #dependencies}.
     */
    @Override
    public String[] getDependencies() {
        return Arrays.copyOf(this.dependencies, this.dependencies.length);
    }

    /**
     *
     * @return {@link #optionalDependencies}.
     */
    @Override
    public String[] getOptionalDependencies() {
        return Arrays.copyOf(this.optionalDependencies, this.optionalDependencies.length);
    }

    /**
     *
     * @return {@link #authors}.
     */
    @Override
    public Optional<String[]> getAuthors() {
        return Optional.ofNullable(this.authors).map(arr -> Arrays.copyOf(arr, arr.length));
    }

    // ~ Setters ~

    /**
     *
     * @param name
     *            the {@link #name} to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @param displayName
     *            the {@link #displayName} to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @param version
     *            the {@link #version} to set
     */
    public void setVersion(final Version version) {
        this.version = version;
    }

    /**
     *
     * @param main
     *            the {@link #main} to set
     */
    public void setMain(final String main) {
        this.main = main;
    }

    /**
     *
     * @param pluginType
     *            the {@link #pluginType} to set
     */
    public void setPluginType(final PluginType pluginType) {
        this.pluginType = pluginType;
    }

    /**
     *
     * @param dependencies
     *            the {@link #dependencies} to set
     */
    public void setDependencies(final String[] dependencies) {
        this.dependencies = dependencies;
    }

    /**
     *
     * @param optionalDependencies
     *            the {@link #optionalDependencies} to set
     */
    public void setOptionalDependencies(final String[] optionalDependencies) {
        this.optionalDependencies = optionalDependencies;
    }

    /**
     *
     * @param authors
     *            the {@link #authors} to set
     */
    public void setAuthors(final String[] authors) {
        this.authors = authors;
    }
}
