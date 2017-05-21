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
        this.config.setStrings("prop3", "Hello,", " World!");

        Assert.assertEquals("Property One", this.config.getString("prop1", "NONE"));
        Assert.assertEquals(12.34, this.config.getDouble("prop2", -1), PropertiesPluginConfigTest.EPSILON);
        Assert.assertArrayEquals(new String[] { "Hello,", " World!" }, this.config.getStrings("prop3", "NONE"));
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
}
