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
 * Utility class for working with arrays.
 *
 */
public final class ArrayUtil {
    /**
     * Constructor of ArrayUtil.
     *
     */
    private ArrayUtil() {
        // Nothing to do.
    }

    /**
     * Converts the given array of booleans into an array of {@link String
     * Strings} (an array of <code>"true"</code> and <code>"false"</code>).
     *
     * @param booleans
     *            The boolean array. Must not be <code>null</code>.
     * @return The {@link String} array.
     */
    public static String[] toStringArray(final boolean[] booleans) {
        if (booleans == null) {
            throw new IllegalArgumentException("Booleans must not be null!");
        }

        final String[] result = new String[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            result[i] = String.valueOf(booleans[i]);
        }
        return result;
    }

    /**
     * Parses the given array of {@link String Strings} into an array of
     * booleans (treating <code>"true"</code> as <code>true</code> and
     * everything else as <code>false</code>).
     *
     * @param strings
     *            The {@link String} array. Must not be <code>null</code>.
     * @return The <code>boolean</code> array.
     */
    public static boolean[] toBooleanArray(final String[] strings) {
        if (strings == null) {
            throw new IllegalArgumentException("Strings must not be null!");
        }

        final boolean[] result = new boolean[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Boolean.parseBoolean(strings[i]);
        }
        return result;
    }
}
