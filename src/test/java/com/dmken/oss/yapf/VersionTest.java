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
package com.dmken.oss.yapf;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class VersionTest {
    @Test
    public void testConstructor() {
        final Version version = new Version(1, 2, 3);

        Assert.assertEquals(1, version.getMajor());
        Assert.assertEquals(2, version.getMinor());
        Assert.assertEquals(3, version.getBugfix());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_error_invalid1() {
        new Version(-1, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_error_invalid2() {
        new Version(0, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_error_invalid3() {
        new Version(0, 0, -1);
    }

    @Test
    public void testConstructor_parse1() {
        final Version version = new Version("1.2.3");

        Assert.assertEquals(1, version.getMajor());
        Assert.assertEquals(2, version.getMinor());
        Assert.assertEquals(3, version.getBugfix());
    }

    @Test
    public void testConstructor_parse2() {
        final Version version = new Version("1.2");

        Assert.assertEquals(1, version.getMajor());
        Assert.assertEquals(2, version.getMinor());
        Assert.assertEquals(0, version.getBugfix());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_parse_error_invalid1() {
        new Version("1.2.A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_parse_error_invalid2() {
        new Version("1.-2.3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_parse_error_invalid3() {
        new Version(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_parse_error_invalid4() {
        new Version("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_parse_error_invalid5() {
        new Version("1.2.3.4");
    }

    @Test
    public void testCompareTo_1() {
        // Generated tests.

        final Version v1 = new Version("7.4.8");
        final Version v2 = new Version("2.8");
        final Version v3 = new Version("4.5.7");
        final Version v4 = new Version("9.7");
        final Version v5 = new Version("1.9.3");

        Assert.assertTrue(v1.compareTo(v1) == 0);
        Assert.assertTrue(v1.compareTo(v2) > 0);
        Assert.assertTrue(v1.compareTo(v3) > 0);
        Assert.assertTrue(v1.compareTo(v4) < 0);
        Assert.assertTrue(v1.compareTo(v5) > 0);

        Assert.assertTrue(v2.compareTo(v1) < 0);
        Assert.assertTrue(v2.compareTo(v2) == 0);
        Assert.assertTrue(v2.compareTo(v3) < 0);
        Assert.assertTrue(v2.compareTo(v4) < 0);
        Assert.assertTrue(v2.compareTo(v5) > 0);

        Assert.assertTrue(v3.compareTo(v1) < 0);
        Assert.assertTrue(v3.compareTo(v2) > 0);
        Assert.assertTrue(v3.compareTo(v3) == 0);
        Assert.assertTrue(v3.compareTo(v4) < 0);
        Assert.assertTrue(v3.compareTo(v5) > 0);

        Assert.assertTrue(v4.compareTo(v1) > 0);
        Assert.assertTrue(v4.compareTo(v2) > 0);
        Assert.assertTrue(v4.compareTo(v3) > 0);
        Assert.assertTrue(v4.compareTo(v4) == 0);
        Assert.assertTrue(v4.compareTo(v5) > 0);

        Assert.assertTrue(v5.compareTo(v1) < 0);
        Assert.assertTrue(v5.compareTo(v2) < 0);
        Assert.assertTrue(v5.compareTo(v3) < 0);
        Assert.assertTrue(v5.compareTo(v4) < 0);
        Assert.assertTrue(v5.compareTo(v5) == 0);
    }

    @Test
    public void testCompareTo_edgecase() {
        final Version v1 = new Version("1");
        final Version v2 = new Version("1.0.1");
        final Version v3 = new Version("0");

        Assert.assertTrue(v1.compareTo(v1) == 0);
        Assert.assertTrue(v1.compareTo(v2) < 0);
        Assert.assertTrue(v1.compareTo(v3) > 0);

        Assert.assertTrue(v2.compareTo(v1) > 0);
        Assert.assertTrue(v2.compareTo(v2) == 0);
        Assert.assertTrue(v2.compareTo(v3) > 0);

        Assert.assertTrue(v3.compareTo(v1) < 0);
        Assert.assertTrue(v3.compareTo(v2) < 0);
        Assert.assertTrue(v3.compareTo(v3) == 0);
    }
}
