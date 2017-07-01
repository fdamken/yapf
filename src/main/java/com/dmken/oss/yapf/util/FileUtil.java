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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

/**
 * Utility class for working with files.
 *
 */
public final class FileUtil {
    /**
     * Constructor of FileUtil.
     *
     */
    private FileUtil() {
        // Nothing to do.
    }

    /**
     * Checks whether the given file is a valid JAR file.
     *
     * @param file
     *            The file to check
     * @return Whether the given file is a valid JAR file or not. If
     *         <code>null</code>, <code>false</code> is returned.
     * @throws IOException
     *             If any I/O error occurs.
     */
    public static boolean isJarFile(final Path file) throws IOException {
        if (file == null || !Files.isRegularFile(file)) {
            return false;
        }

        try (final JarFile jar = new JarFile(file.toFile())) {
            return true;
        } catch (final ZipException ex) {
            return false;
        }
    }
}
