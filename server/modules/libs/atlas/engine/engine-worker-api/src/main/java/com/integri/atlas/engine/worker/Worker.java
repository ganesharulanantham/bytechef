/*
 * Copyright 2021 <your company/name>.
 *
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
 */

package com.integri.atlas.engine.worker;

import com.integri.atlas.engine.core.event.EventPublisher;
import com.integri.atlas.engine.core.message.broker.MessageBroker;
import com.integri.atlas.engine.core.task.TaskExecution;
import com.integri.atlas.engine.core.task.evaluator.TaskEvaluator;
import com.integri.atlas.engine.worker.task.handler.TaskHandlerResolver;
import java.util.concurrent.ExecutorService;

/**
 * @author Ivica Cardic
 */
public interface Worker {
    void handle(TaskExecution taskExecution);

    interface Builder {
        Builder withTaskHandlerResolver(TaskHandlerResolver aTaskHandlerResolver);

        Builder withTaskEvaluator(TaskEvaluator aTaskEvaluator);

        Builder withMessageBroker(MessageBroker aMessageBroker);

        Builder withEventPublisher(EventPublisher aEventPublisher);

        Builder withExecutors(ExecutorService aExecutors);

        Worker build();
    }
}
