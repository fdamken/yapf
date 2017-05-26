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
package com.dmken.oss.yapf.meta;

import com.dmken.oss.yapf.PluginMeta;

/**
 * An abstract {@link PluginMeta} implementing {@link #hashCode()} and
 * {@link #equals(Object)}.
 *
 */
public abstract class AbstractPluginMeta implements PluginMeta {
    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = prime * result + ((this.getPluginType() == null) ? 0 : this.getPluginType().hashCode());
        result = prime * result + ((this.getVersion() == null) ? 0 : this.getVersion().hashCode());
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
        if (!(obj instanceof PluginMeta)) {
            return false;
        }
        final PluginMeta other = (PluginMeta) obj;
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (this.getPluginType() != other.getPluginType()) {
            return false;
        }
        if (this.getVersion() == null) {
            if (other.getVersion() != null) {
                return false;
            }
        } else if (!this.getVersion().equals(other.getVersion())) {
            return false;
        }
        return true;
    }
}
