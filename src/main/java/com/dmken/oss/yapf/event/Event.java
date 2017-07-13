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

import com.dmken.oss.yapf.util.EventUtil;

/**
 * Represents an event that can be fired using {@link EventUtil#fire(Event)}.
 * 
 * <p>
 * How the firing works depends on the environment the code is running in (e.g.
 * in a Spring environment, the event is published as an application event).
 * </p>
 *
 */
public interface Event {
    /**
     * The name of the event. Mostly the class name.
     *
     * @return The name of the event. Mostly the class name.
     */
    String getName();
}
