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

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ArrayUtilTest {
    @Test
    public void testToStringArray_empty() {
        final boolean[] arr = {};
        final String[] expected = {};

        final String[] actual = ArrayUtil.toStringArray(arr);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testToStringArray_full() {
        final boolean[] arr = { true, false, true, true, false };
        final String[] expected = { "true", "false", "true", "true", "false" };

        final String[] actual = ArrayUtil.toStringArray(arr);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringArray_error_null() {
        ArrayUtil.toStringArray(null);
    }

    @Test
    public void testToBooleanArray_empty() {
        final String[] arr = {};
        final boolean[] expected = {};

        final boolean[] actual = ArrayUtil.toBooleanArray(arr);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testToBooleanArray_full() {
        final String[] arr = { "true", "false", "true", "true", "false" };
        final boolean[] expected = { true, false, true, true, false };

        final boolean[] actual = ArrayUtil.toBooleanArray(arr);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testToBooleanArray_different() {
        final String[] arr = { "true", "1", "true", "true", "0", null };
        final boolean[] expected = { true, false, true, true, false, false };

        final boolean[] actual = ArrayUtil.toBooleanArray(arr);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToBooleanArray_error_null() {
        ArrayUtil.toBooleanArray(null);
    }
}
