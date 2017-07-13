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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmken.oss.yapf.util.Environment;

/**
 * Spring auto-configuration for YAPF.
 * 
 * <p>
 * This creates all beans for plugin loading and plugin management, populates
 * the {@link SpringContextHolder} as a bean and prepares YAPF to work seamless
 * with Spring.
 * </p>
 *
 * <p>
 * <b> NOTE: Do not access this class without checking for a valid Spring
 * environment using {@link Environment#isSpring()} first! </b>
 * </p>
 */
@SuppressWarnings("javadoc")
@Configuration
public class YapfAutoConfiguration {
    @Bean
    public SpringContextHolder springContextHolder(final ApplicationContext applicationContext,
            final ApplicationEventPublisher applicationEventPublisher) {
        return new SpringContextHolder(applicationContext, applicationEventPublisher);
    }
}
