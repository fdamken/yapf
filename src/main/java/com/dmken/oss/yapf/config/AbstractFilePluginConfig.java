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

import com.dmken.oss.yapf.PluginConfig;

/**
 * An abstract {@link PluginConfig} using files for storage.
 *
 */
public abstract class AbstractFilePluginConfig extends AbstractPluginConfig {
    /**
     * The path where to save the configuration.
     *
     */
    private final Path file;

    /**
     * Constructor of AbstractPluginConfig.
     *
     * @param file
     *            See {@link #file}.
     */
    public AbstractFilePluginConfig(final Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }

        this.file = file;
    }

    /**
     *
     * @return {@link #file}.
     */
    protected Path getFile() {
        return this.file;
    }
}
