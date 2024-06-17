/*
 * Copyright 2023-present ByteChef Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.atlas.coordinator.event;

import com.bytechef.atlas.coordinator.message.route.TaskCoordinatorMessageRoute;
import com.bytechef.message.event.MessageEvent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public abstract class AbstractEvent implements MessageEvent<TaskCoordinatorMessageRoute> {

    protected LocalDateTime createDate;
    protected Map<String, Object> metadata = new HashMap<>();
    protected TaskCoordinatorMessageRoute route;

    protected AbstractEvent() {
    }

    public AbstractEvent(TaskCoordinatorMessageRoute route) {
        this.createDate = LocalDateTime.now();
        this.route = route;
    }

    @Override
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public TaskCoordinatorMessageRoute getRoute() {
        return route;
    }

    public Map<String, Object> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    @Override
    public Object getMetadata(String name) {
        return metadata.get(name);
    }

    @Override
    public void putMetadata(String name, Object value) {
        metadata.put(name, value);
    }
}
