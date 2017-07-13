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
package com.dmken.oss.yapf.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.dmken.oss.yapf.event.PostPluginLoadEvent;
import com.dmken.oss.yapf.event.PrePluginLoadEvent;

@SuppressWarnings("javadoc")
@Component
public class MethodInvocationCountingApplicationEventListener {
    private int onPostPluginLoadEventInvocations = 0;
    private int onPrePluginLoadEventInvocations = 0;

    @EventListener
    public void onPostPluginLoadEvent(@SuppressWarnings("unused") final PostPluginLoadEvent event) {
        this.onPostPluginLoadEventInvocations++;
    }

    @EventListener
    public void onPrePluginLoadEvent(@SuppressWarnings("unused") final PrePluginLoadEvent event) {
        this.onPrePluginLoadEventInvocations++;
    }

    /**
     *
     * @return {@link #onPostPluginLoadEventInvocations}.
     */
    public int getOnPostPluginLoadEventInvocations() {
        return this.onPostPluginLoadEventInvocations;
    }

    /**
     *
     * @return {@link #onPrePluginLoadEventInvocations}.
     */
    public int getOnPrePluginLoadEventInvocations() {
        return this.onPrePluginLoadEventInvocations;
    }
}
