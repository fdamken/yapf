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

/**
 * Utility class for environment checks (e.g. whether a framework like Spring is
 * available).
 *
 */
public final class Environment {
    /**
     * The full qualified name of the application context interface in Spring.
     * The application context is included in every real Spring application.
     *
     */
    private static final String SPRING = "org.springframework.context.ApplicationContext";

    /**
     * Constructor of Environment.
     *
     */
    private Environment() {
        // Nothing to do.
    }

    /**
     * Checks whether the given class loader supports Spring.
     *
     * @param classLoader
     *            The class loader to use for checking.
     * @return Whether the given class loader supports Spring.
     */
    public static boolean isSpring(final ClassLoader classLoader) {
        return Environment.isClassAccessible(Environment.SPRING, classLoader);
    }

    /**
     * Checks whether the current thread supports Spring.
     *
     * @return Whether the current thread supports Spring or not.
     */
    public static boolean isSpring() {
        return Environment.isSpring(null);
    }

    /**
     * Checks whether a class with the given (full qualified) class name is
     * available. This does not initialize the class!
     *
     * @param className
     *            The full qualified name of the class to check.
     * @param classLoader
     *            The class loader to use. If <code>null</code>, the current
     *            context class loader is used.
     * @return Whether the class is accessible.
     */
    private static boolean isClassAccessible(final String className, final ClassLoader classLoader) {
        try {
            Class.forName(className, false, classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader);
            return true;
        } catch (final ClassNotFoundException dummy) {
            return false;
        }
    }
}
