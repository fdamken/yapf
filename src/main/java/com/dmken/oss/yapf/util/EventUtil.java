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
package com.dmken.oss.yapf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmken.oss.yapf.event.Event;
import com.dmken.oss.yapf.spring.SpringContextHolder;

/**
 * Provides multiple methods for working with events.
 *
 */
public final class EventUtil {
    /**
     * The logger.
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventUtil.class);

    /**
     * Constructor of EventUtil.
     *
     */
    private EventUtil() {
        // Nothing to do.
    }

    /**
     * Fires the given events.
     * 
     * <ol>
     * <li>If Spring is on the classpath, it fires the event as an application
     * event in Spring.</li>
     * <li>Otherwise it does nothing.</li>
     * </ol>
     *
     * @param event
     *            The event to fire.
     */
    public static void fire(final Event event) {
        if (Environment.isSpring()) {
            EventUtil.fireSpring(event);
        }
    }

    /**
     * Fires the given event as an application event.
     * 
     * <p>
     * <b> NOTE: Make sure Spring exists before invoking! </b>
     * </p>
     *
     * @param event
     *            The event to fire.
     */
    private static void fireSpring(final Event event) {
        EventUtil.LOGGER.debug("Firing YAPF application event {}.", event.getName());

        SpringContextHolder.getInstance().getApplicationEventPublisher().publishEvent(event);
    }
}
