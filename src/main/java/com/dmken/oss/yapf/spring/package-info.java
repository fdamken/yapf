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
/**
 * Contains Spring-related stuff.
 * 
 * <p>
 * <b> NOTE: Do not access classes in this package without checking that Spring
 * exists on the classpath first! If Spring does not exist on the classpath,
 * loading classes of this package will result in a
 * {@link java.lang.NoClassDefFoundError}. </b>
 * </p>
 *
 * @see com.dmken.oss.yapf.util.Environment#isSpring(ClassLoader)
 * @see com.dmken.oss.yapf.util.Environment#isSpring()
 */
package com.dmken.oss.yapf.spring;
