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
package com.dmken.oss.yapf.config;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.dmken.oss.yapf.PluginConfig;

@SuppressWarnings("javadoc")
public class PropertiesPluginConfigTest {
    private static final double EPSILON = 10e-10;

    @Rule
    public TemporaryFolder dir = new TemporaryFolder();

    private Path file;
    private PluginConfig config;

    @Before
    public void setUp() throws Exception {
        this.file = this.dir.newFile().toPath();
        this.config = new PropertiesPluginConfig(this.file);
    }

    @Test
    public void testGetSet() {
        this.config.setString("prop1", "Property One");
        this.config.setDouble("prop2", 12.34);
        this.config.setStrings("prop3", "'A,", "B", ",,C");

        Assert.assertEquals("Property One", this.config.getString("prop1", "NONE"));
        Assert.assertEquals(12.34, this.config.getDouble("prop2", -1), PropertiesPluginConfigTest.EPSILON);
        Assert.assertArrayEquals(new String[] { "'A,", "B", ",,C" }, this.config.getStrings("prop3", "NONE"));

        this.config.setString("prop1", "Property One Update");

        Assert.assertEquals("Property One Update", this.config.getString("prop1", "NONE"));
    }

    @Test
    public void testDefaults() {
        this.config.defineDefault("prop1", "Property One");
        this.config.defineDefault("prop2", "2");
        this.config.defineDefaults("prop3", "One", "Two", "Three");

        Assert.assertEquals("Property One", this.config.getString("prop1", "NONE"));
        Assert.assertEquals(2, this.config.getInt("prop2", -1));
        Assert.assertArrayEquals(new String[] { "One", "Two", "Three" }, this.config.getStrings("prop3", "NONE"));
        Assert.assertEquals(12.34, this.config.getDouble("undefined", 12.34), PropertiesPluginConfigTest.EPSILON);
    }

    @Test
    public void testDefaultDefaults() {
        Assert.assertNull(this.config.getString("undefined"));
        Assert.assertArrayEquals(new String[0], this.config.getStrings("undefined"));
        Assert.assertEquals(0, this.config.getLong("undefined"));
        Assert.assertEquals(0, this.config.getInt("undefined"));
        Assert.assertEquals(0, this.config.getDouble("undefined"), PropertiesPluginConfigTest.EPSILON);
        Assert.assertFalse(this.config.getBoolean("undefined"));
    }

    @Test
    public void testSave() throws Exception {
        this.config.setString("prop1", "This is a String!");
        this.config.setStrings("prop2", "'A,", "B", ",,C");
        this.config.setLong("prop3", Long.MAX_VALUE);
        this.config.setInt("prop4", Integer.MAX_VALUE);
        this.config.setDouble("prop5", Double.NEGATIVE_INFINITY);
        this.config.setBoolean("prop6", true);
        this.config.setComment("COMMENT Hello, World! I am a comment.");

        this.config.save();

        boolean commentChecked = false;
        boolean prop1Checked = false;
        boolean prop2Checked = false;
        boolean prop3Checked = false;
        boolean prop4Checked = false;
        boolean prop5Checked = false;
        boolean prop6Checked = false;
        try (final BufferedReader reader = Files.newBufferedReader(this.file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                final String expected;
                if (line.startsWith("#COMMENT")) {
                    expected = "#COMMENT Hello, World! I am a comment.";
                    commentChecked = true;
                } else if (line.startsWith("#")) {
                    // This is the date line that is nearly impossible to check
                    // and cannot be skipped when storing.
                    expected = line;
                } else if (line.startsWith("prop1")) {
                    expected = "prop1=This is a String\\!";
                    prop1Checked = true;
                } else if (line.startsWith("prop2")) {
                    expected = "prop2=''A',, B, ',',C"; // TODO
                    prop2Checked = true;
                } else if (line.startsWith("prop3")) {
                    expected = "prop3=" + Long.MAX_VALUE;
                    prop3Checked = true;
                } else if (line.startsWith("prop4")) {
                    expected = "prop4=" + Integer.MAX_VALUE;
                    prop4Checked = true;
                } else if (line.startsWith("prop5")) {
                    expected = "prop5=" + Double.NEGATIVE_INFINITY;
                    prop5Checked = true;
                } else if (line.startsWith("prop6")) {
                    expected = "prop6=" + true;
                    prop6Checked = true;
                } else {
                    Assert.fail("Unexpected line: <" + line + ">!");
                    // This line will never be reached as Assert.fail(...)
                    // throws an exception in any case.
                    return;
                }
                Assert.assertEquals(expected, line);
            }
        }
        if (!commentChecked || !prop1Checked || !prop2Checked || !prop3Checked || !prop4Checked || !prop5Checked
                || !prop6Checked) {
            Assert.fail(String.format(
                    "Not all properties where saved! "
                            + "(comment, prop1, prop2, prop3, prop4, prop5, prop6): (%b, %b, %b, %b, %b, %b, %b)",
                    commentChecked, prop1Checked, prop2Checked, prop3Checked, prop4Checked, prop5Checked, prop6Checked));
        }
    }

    @Test
    public void testLoad() throws Exception {
        this.config.load(PropertiesPluginConfigTest.class.getResourceAsStream("/test-plugin-config.properties"));

        Assert.assertEquals("This is a String!", this.config.getString("prop1"));
        Assert.assertArrayEquals(new String[] { "'A,", "B", ",,C" }, this.config.getStrings("prop2"));
        Assert.assertEquals(Long.MAX_VALUE, this.config.getLong("prop3"));
        Assert.assertEquals(Integer.MAX_VALUE, this.config.getInt("prop4"));
        Assert.assertEquals(Double.NEGATIVE_INFINITY, this.config.getDouble("prop5"), PropertiesPluginConfigTest.EPSILON);
        Assert.assertTrue(this.config.getBoolean("prop6"));
    }
}
