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
package com.dmken.oss.yapf.event;

import com.dmken.oss.yapf.Plugin;

/**
 * Fired if a plugin was loaded.
 *
 */
public class PostPluginLoadEvent extends AbstractEvent {
    /**
     * The plugin that was loaded.
     * 
     */
    private final Plugin plugin;

    /**
     * Constructor of PostPluginLoadEvent.
     *
     * @param plugin
     *            {@link #plugin}.
     */
    public PostPluginLoadEvent(final Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @return {@link #plugin}.
     */
    public Plugin getPlugin() {
        return this.plugin;
    }
}
