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
package com.dmken.oss.yapf.event;

import java.util.List;

/**
 * Marks an event as cancelable. That is if the event is cancelled, the code
 * after the event firing is not executed.
 *
 */
public interface Cancelable {
    /**
     *
     * @return Whether the event was cancelled.
     */
    boolean isCancelled();

    /**
     *
     * @return All reasons why the event was cancelled.
     */
    List<String> getCancelMessages();

    /**
     * Cancels the event and appends the given message to the reasons of the
     * cancelation.
     *
     * @param message
     *            The reason why the event was cancelled.
     */
    void cancel(final String message);
}
