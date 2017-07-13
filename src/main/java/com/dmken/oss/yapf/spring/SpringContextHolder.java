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

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

import com.dmken.oss.yapf.util.Environment;

/**
 * Holds {@link ApplicationContext Spring's application context} and populates
 * it to YAPF for firing application events.
 * 
 * <p>
 * <b> NOTE: Do not access this class without checking for a valid Spring
 * environment using {@link Environment#isSpring()} first! </b>
 * </p>
 *
 */
public class SpringContextHolder {
    /**
     * An instance of this class.
     * 
     */
    private static SpringContextHolder instance;

    /**
     * The application context.
     * 
     */
    private final ApplicationContext applicationContext;
    /**
     * The application event publisher.
     * 
     */
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructor of SpringContextHolder.
     *
     * @param applicationContext
     *            {@link #applicationContext}.
     * @param applicationEventPublisher
     *            {@link #applicationEventPublisher}.
     */
    public SpringContextHolder(final ApplicationContext applicationContext,
            final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationContext = applicationContext;
        this.applicationEventPublisher = applicationEventPublisher;

        SpringContextHolder.instance = this;
    }

    /**
     *
     * @return {@link #instance}.
     */
    public static SpringContextHolder getInstance() {
        return SpringContextHolder.instance;
    }

    /**
     *
     * @return {@link #applicationContext}.
     */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     *
     * @return {@link #applicationEventPublisher}.
     */
    public ApplicationEventPublisher getApplicationEventPublisher() {
        return this.applicationEventPublisher;
    }
}
