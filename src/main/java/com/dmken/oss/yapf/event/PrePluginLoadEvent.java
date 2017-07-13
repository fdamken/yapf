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

import com.dmken.oss.yapf.PluginMeta;
import com.dmken.oss.yapf.meta.UnmodifiablePluginMeta;

/**
 * Fired right before a plugin is loaded. This way the plugin loading process
 * can be interrupted as the event is {@link Cancelable cancelable}.
 *
 */
public class PrePluginLoadEvent extends AbstractCancelableEvent {
    /**
     * The metadata of the plugin to be loaded.
     * 
     */
    private final PluginMeta pluginMeta;

    /**
     * Constructor of PrePluginLoadEvent.
     *
     * @param pluginMeta
     *            {@link #pluginMeta}.
     */
    public PrePluginLoadEvent(final PluginMeta pluginMeta) {
        this.pluginMeta = pluginMeta;
    }

    /**
     *
     * @return {@link #pluginMeta}.
     */
    public PluginMeta getPluginMeta() {
        return new UnmodifiablePluginMeta(this.pluginMeta);
    }
}
