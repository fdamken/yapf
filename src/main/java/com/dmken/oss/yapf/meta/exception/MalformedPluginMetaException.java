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
package com.dmken.oss.yapf.meta.exception;

@SuppressWarnings("javadoc")
public class MalformedPluginMetaException extends Exception {
    private static final long serialVersionUID = 1605786768643482990L;

    public MalformedPluginMetaException(final String pluginName, final String message, final Throwable cause) {
        super(MalformedPluginMetaException.makeMessage(pluginName, message), cause);
    }

    public MalformedPluginMetaException(final String pluginName, final String message) {
        super(MalformedPluginMetaException.makeMessage(pluginName, message));
    }

    private static String makeMessage(final String pluginName, final String message) {
        return "<" + pluginName + ">: " + message;
    }
}
