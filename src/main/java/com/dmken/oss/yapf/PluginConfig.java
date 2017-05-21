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

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.dmken.oss.yapf.util.ArrayUtil;

/**
 * Represents the configuration of a plugin.
 *
 */
public interface PluginConfig {
    /**
     * Loads this plugin configuration from disk.
     *
     * <p>
     * The location where the configuration is stored is defined at the creation
     * of the configuration and must not change.
     * </p>
     * 
     * <p>
     * If the configuration does not exist on disk, an empty configuration is
     * returned.
     * </p>
     * 
     * @throws IOException
     *             If any I/O error occurs.
     */
    void load() throws IOException;

    /**
     * Persists this plugin configuration to disk.
     * 
     * <p>
     * The location where to store the configuration is defined at the creation
     * of the configuration and must not change.
     * </p>
     * 
     * @throws IOException
     *             If any I/O error occurs.
     */
    void save() throws IOException;

    /**
     * Sets the default value of the property <code>property</code> to the value
     * <code>value</code>. If a default value is defined this way, it is
     * preferred over the default value that is passed to any
     * <code>getXXX(...)</code> method.
     * 
     * <p>
     * <b> NOTE: This method is not type-safe! You have to provide either a
     * {@link String} or, if you want to save a number, a valid number in a
     * format it can be parsed by the desired <code>parseXXX(...)</code>
     * method. </b>
     * </p>
     *
     * @param property
     *            The name of the property.
     * @param value
     *            The default value to define.
     */
    void defineDefault(final String property, final String value);

    /**
     * Sets the default value of the property <code>property</code> to the value
     * <code>value</code>. If a default value is defined this way, it is
     * preferred over the default value that is passed to any
     * <code>getXXX(...)</code> method.
     * 
     * <p>
     * <b> NOTE: This method is not type-safe! You have to provide either an
     * array of {@link String Strings} or, if you want to save a number, an
     * array of valid numbers in a format it can be parsed by the desired
     * <code>parseXXX(...)</code> method. </b>
     * </p>
     *
     * @param property
     *            The name of the property.
     * @param value
     *            The default value to define.
     */
    void defineDefaults(final String property, final String... value);

    /**
     * Gets the property <code>property</code> as a {@link String} and uses
     * <code>value</code> as the default value (preferring the defined default
     * value).
     * 
     * <p>
     * If a default value was defined using
     * {@link #defineDefault(String, String)}, it is preferred over the passed
     * default value.
     * </p>
     *
     * @param property
     *            The name of the property to get.
     * @param value
     *            The default value to return if the property was not found.
     * @return The value of the property as a {@link String} or the defined
     *         default value if the property is not set or the given value
     *         <code>value</code> if no default value was defined.
     */
    String getString(final String property, final String value);

    /**
     * Sets the property <code>property</code> to the given value
     * <code>value</code>.
     *
     * @param property
     *            The name of the property to set.
     * @param value
     *            The value to set the property to.
     */
    void setString(final String property, final String value);

    /**
     * Sets a comment that is written into the configuration file.
     *
     * @param comment
     *            The comment.
     */
    void setComment(final String comment);

    // ~ Defaults ~

    /**
     * Delegates to {@link #load()}. This is just for semantic reasons.
     * 
     * @throws IOException
     *             If any I/O error occurs.
     * @see #load()
     */
    default void reload() throws IOException {
        this.load();
    }

    // ~ Default Getters ~

    @SuppressWarnings("javadoc")
    default String getString(final String property) {
        return this.getString(property, null);
    }

    /*
     * Developer Note: This method splits a single string and parses it as it
     * where comma-separated (where to commas escape one comma). This method
     * should be replaced when the wrapped file type supports arrays.
     */
    @SuppressWarnings("javadoc")
    default String[] getStrings(final String property, final String... value) {
        final String str = this.getString(property, null);
        if (str == null) {
            return value == null ? null : Arrays.copyOf(value, value.length);
        }
        return Arrays.stream(str.split(",")) //
                // This allows escaping a "," with another "," (",,").
                .map(s -> s.isEmpty() ? "," : s.trim()) //
                .toArray(String[]::new);
    }

    @SuppressWarnings("javadoc")
    default long getLong(final String property, final long value) {
        return Long.parseLong(this.getString(property, String.valueOf(value)));
    }

    @SuppressWarnings("javadoc")
    default long getLong(final String property) {
        return this.getLong(property, 0);
    }

    @SuppressWarnings("javadoc")
    default long[] getLongs(final String property, final long... value) {
        final String[] def = value == null ? null : Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new);
        final String[] strings = this.getStrings(property, def);
        if (strings == null) {
            return value;
        }
        return Arrays.stream(strings).mapToLong(Long::parseLong).toArray();
    }

    @SuppressWarnings("javadoc")
    default int getInt(final String property, final int value) {
        return Integer.parseInt(this.getString(property, String.valueOf(value)));
    }

    @SuppressWarnings("javadoc")
    default int getInt(final String property) {
        return this.getInt(property, 0);
    }

    @SuppressWarnings("javadoc")
    default int[] getInts(final String property, final int... value) {
        final String[] def = value == null ? null : Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new);
        final String[] strings = this.getStrings(property, def);
        if (strings == null) {
            return value;
        }
        return Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
    }

    @SuppressWarnings("javadoc")
    default double getDouble(final String property, final double value) {
        return Double.parseDouble(this.getString(property, String.valueOf(value)));
    }

    @SuppressWarnings("javadoc")
    default double getDouble(final String property) {
        return this.getDouble(property, 0);
    }

    @SuppressWarnings("javadoc")
    default double[] getDoubles(final String property, final double... value) {
        final String[] def = value == null ? null : Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new);
        final String[] strings = this.getStrings(property, def);
        if (strings == null) {
            return value;
        }
        return Arrays.stream(strings).mapToDouble(Double::parseDouble).toArray();
    }

    @SuppressWarnings("javadoc")
    default boolean getBoolean(final String property, final boolean value) {
        return Boolean.parseBoolean(this.getString(property, String.valueOf(value)));
    }

    @SuppressWarnings("javadoc")
    default boolean getBoolean(final String property) {
        return this.getBoolean(property, false);
    }

    @SuppressWarnings("javadoc")
    default boolean[] getBooleans(final String property, final boolean... value) {
        final String[] def = value == null ? null : ArrayUtil.toStringArray(value);
        final String[] strings = this.getStrings(property, def);
        if (strings == null) {
            return value;
        }
        return ArrayUtil.toBooleanArray(strings);
    }

    // ~ Default Setters ~

    /*
     * Developer Note: This method creates a single string of the given strings
     * by joining them with a comma as the delimiter. A comma is escaped using
     * two commas. This should be replaced if the wrapped file type supports
     * saving arrays.
     */
    @SuppressWarnings("javadoc")
    default void setStrings(final String property, final String... value) {
        this.setString(property, Arrays.stream(value).map(s -> s.replaceAll(",", ",,")).collect(Collectors.joining(", ")));
    }

    @SuppressWarnings("javadoc")
    default void setLong(final String property, final long value) {
        this.setString(property, String.valueOf(value));
    }

    @SuppressWarnings("javadoc")
    default void setLongs(final String property, final long... value) {
        this.setStrings(property, Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new));
    }

    @SuppressWarnings("javadoc")
    default void setInt(final String property, final int value) {
        this.setString(property, String.valueOf(value));
    }

    @SuppressWarnings("javadoc")
    default void setInts(final String property, final int... value) {
        this.setStrings(property, Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new));
    }

    @SuppressWarnings("javadoc")
    default void setDouble(final String property, final double value) {
        this.setString(property, String.valueOf(value));
    }

    @SuppressWarnings("javadoc")
    default void setDoubles(final String property, final double... value) {
        this.setStrings(property, Arrays.stream(value).mapToObj(String::valueOf).toArray(String[]::new));
    }

    @SuppressWarnings("javadoc")
    default void setBoolean(final String property, final boolean value) {
        this.setString(property, String.valueOf(value));
    }

    @SuppressWarnings("javadoc")
    default void setBooleans(final String property, final boolean... value) {
        this.setStrings(property, ArrayUtil.toStringArray(value));
    }
}
