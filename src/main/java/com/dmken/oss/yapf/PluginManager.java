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

import java.util.Collection;
import java.util.Optional;

import com.dmken.oss.yapf.exception.ImplementationConflictException;
import com.dmken.oss.yapf.exception.MissingImplementationException;

/**
 * This is one of the core parts of YAPF as it handles all stuff related to
 * dependency resolving, implementation handling and so on.
 * 
 * <p>
 * <b> NOTE: This class is under high development and its method may change
 * frequently! </b>
 * </p>
 *
 */
public interface PluginManager {
    /**
     * Disables the given plugin.
     *
     * @param plugin
     *            The plugin to disable.
     */
    void disable(final Plugin plugin);

    /**
     * Enables the given plugin.
     *
     * @param plugin
     *            The plugin to enable.
     */
    void enable(final Plugin plugin);

    /**
     * Defines an implementation of the given <code>definition</code>.
     *
     * @param <T>
     *            The type of the definition interface.
     * @param implementingPlugin
     *            The plugin that is implementing the given definition.
     * @param definition
     *            The definition interface that the implementation is for. This
     *            must be an interface!
     * @param implementation
     *            The actual implementation.
     * @throws ImplementationConflictException
     *             If an implementation of the specified definition does already
     *             exist.
     */
    <T> void defineImplementation(final Plugin implementingPlugin, final Class<T> definition, final T implementation)
            throws ImplementationConflictException;

    /**
     * Finds an implementation of the given <code>definition</code>.
     *
     * @param <T>
     *            The type of the definition interface.
     * @param definition
     *            The definition interface.
     * @return The definition implementation, if found.
     * @throws MissingImplementationException
     *             If no implementation was found.
     */
    <T> T get(final Class<T> definition) throws MissingImplementationException;

    /**
     * Revokes the implementation of the given <code>definition</code>.
     *
     * @param <T>
     *            The type of the definition interface.
     * @param definition
     *            The definition interface to revoke the implementation for.
     */
    <T> void revokeImplementation(final Class<T> definition);

    /**
     * Revokes all implementations of the given plugin.
     *
     * @param implementingPlugin
     *            The implementing plugin to revoke all implementations for.
     */
    void revokeImplementations(final Plugin implementingPlugin);

    /**
     * Finds the plugin with the given name (including suffix).
     *
     * @param name
     *            The name of the plugin to find.
     * @return The plugin with the given name or an empty {@link Optional} if it
     *         was not found.
     */
    Optional<Plugin> getPlugin(final String name);

    /**
     *
     * @return All plugins.
     */
    Collection<Plugin> getPlugins();

    // ~ Defaults ~

    /**
     * Disables all plugins.
     *
     */
    default void disableAll() {
        this.getPlugins().forEach(this::disable);
    }

    /**
     * Enables all plugins.
     *
     */
    default void enableAll() {
        this.getPlugins().forEach(this::enable);
    }
}
