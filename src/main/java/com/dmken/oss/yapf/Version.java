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

import java.util.Objects;

/**
 * Represents a version with a major, a minor and a bugfix component. This
 * matches the typical versions in the format <code>X.Y.Z</code> where
 * <code>X</code> is the major component, <code>Y</code> is the minor component
 * and <code>Z</code> is the bugfix (or build number) component.
 *
 */
public class Version implements Comparable<Version> {
    /**
     * The major component.
     * 
     */
    private final int major;
    /**
     * The minor component.
     * 
     */
    private final int minor;
    /**
     * The bugfix component.
     * 
     */
    private final int bugfix;

    /**
     * Constructor of Version.
     *
     * @param major
     *            {@link #major}
     * @param minor
     *            {@link #minor}
     * @param bugfix
     *            {@link #bugfix}
     */
    public Version(final int major, final int minor, final int bugfix) {
        this.major = major;
        this.minor = minor;
        this.bugfix = bugfix;
    }

    /**
     * Constructor of Version.
     * 
     * <p>
     * Creates a new version by parsing the given version string in the format
     * <code>X.Y.Z</code>.
     * </p>
     *
     * @param version
     *            The version string to parse.
     * @throws IllegalArgumentException
     *             If the given version string is invalid (e.g.
     *             <code>null</code> or some component are not numbers).
     */
    public Version(final String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version must not be null or empty!");
        }

        final String[] split = version.split("\\.");
        if (split.length > 3) {
            // Less components is okay. Missing components are treated as zero.

            throw new IllegalArgumentException("Version must not contain more than three components!");
        }

        final int major;
        final int minor;
        final int bugfix;
        try {
            if (split.length >= 1) {
                major = Integer.parseInt(split[0]);
            } else {
                major = 0;
            }
            if (split.length >= 2) {
                minor = Integer.parseInt(split[1]);
            } else {
                minor = 0;
            }
            if (split.length >= 3) {
                bugfix = Integer.parseInt(split[2]);
            } else {
                bugfix = 0;
            }
        } catch (final NumberFormatException ex) {
            throw new IllegalArgumentException("Either major, minor or bugfix version component is not a valid integer!");
        }
        if (major < 0 || minor < 0 || bugfix < 0) {
            throw new IllegalArgumentException("Either major, minor or bugfix version component is negative!");
        }

        this.major = major;
        this.minor = minor;
        this.bugfix = bugfix;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Version version) {
        final Version that = Objects.requireNonNull(version);
        int result = 0;
        if (result == 0) {
            result = Integer.compare(this.major, that.major);
        }
        if (result == 0) {
            result = Integer.compare(this.minor, that.minor);
        }
        if (result == 0) {
            result = Integer.compare(this.bugfix, that.bugfix);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.bugfix;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.bugfix;
        result = prime * result + this.major;
        result = prime * result + this.minor;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        final Version other = (Version) obj;
        if (this.bugfix != other.bugfix) {
            return false;
        }
        if (this.major != other.major) {
            return false;
        }
        if (this.minor != other.minor) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return {@link #major}.
     */
    public int getMajor() {
        return this.major;
    }

    /**
     *
     * @return {@link #minor}.
     */
    public int getMinor() {
        return this.minor;
    }

    /**
     *
     * @return {@link #bugfix}.
     */
    public int getBugfix() {
        return this.bugfix;
    }
}
