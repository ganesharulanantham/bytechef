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

package com.integri.atlas.task.handler;

import com.integri.atlas.engine.coordinator.Coordinator;
import com.integri.atlas.engine.coordinator.error.TaskExecutionErrorHandler;
import com.integri.atlas.engine.coordinator.job.Job;
import com.integri.atlas.engine.coordinator.job.executor.DefaultJobExecutor;
import com.integri.atlas.engine.coordinator.job.repository.JobRepository;
import com.integri.atlas.engine.coordinator.task.completion.DefaultTaskCompletionHandler;
import com.integri.atlas.engine.coordinator.task.completion.TaskCompletionHandler;
import com.integri.atlas.engine.coordinator.task.completion.TaskCompletionHandlerChain;
import com.integri.atlas.engine.coordinator.task.dispatcher.DefaultTaskDispatcher;
import com.integri.atlas.engine.coordinator.task.dispatcher.TaskDispatcherChain;
import com.integri.atlas.engine.coordinator.workflow.repository.JSONWorkflowMapper;
import com.integri.atlas.engine.coordinator.workflow.repository.WorkflowMapper;
import com.integri.atlas.engine.core.MapObject;
import com.integri.atlas.engine.core.context.repository.ContextRepository;
import com.integri.atlas.engine.core.error.Error;
import com.integri.atlas.engine.core.json.JSONHelper;
import com.integri.atlas.engine.core.message.broker.MessageBroker;
import com.integri.atlas.engine.core.message.broker.Queues;
import com.integri.atlas.engine.core.task.TaskExecution;
import com.integri.atlas.engine.core.task.dispatcher.TaskDispatcher;
import com.integri.atlas.engine.core.task.dispatcher.TaskDispatcherResolver;
import com.integri.atlas.engine.core.task.evaluator.spel.SpelTaskEvaluator;
import com.integri.atlas.engine.core.task.repository.TaskExecutionRepository;
import com.integri.atlas.engine.worker.Worker;
import com.integri.atlas.engine.worker.WorkerImpl;
import com.integri.atlas.engine.worker.task.handler.DefaultTaskHandlerResolver;
import com.integri.atlas.engine.worker.task.handler.TaskHandler;
import com.integri.atlas.engine.worker.task.handler.TaskHandlerResolverChain;
import com.integri.atlas.file.storage.FileStorageService;
import com.integri.atlas.message.broker.sync.SyncMessageBroker;
import com.integri.atlas.workflow.repository.resource.ResourceBasedWorkflowRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Ivica Cardic
 */
public abstract class BaseTaskIntTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTaskIntTest.class);

    @Autowired
    protected ContextRepository contextRepository;

    @Autowired
    protected JobRepository jobRepository;

    @Autowired
    protected JSONHelper jsonHelper;

    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected TaskExecutionRepository taskExecutionRepository;

    protected List<TaskCompletionHandler> getTaskCompletionHandlers(
        TaskCompletionHandler taskCompletionHandler,
        TaskDispatcher taskDispatcher
    ) {
        return List.of();
    }

    protected List<TaskDispatcherResolver> getTaskDispatcherResolvers(
        MessageBroker coordinatorMessageBroker,
        TaskDispatcher taskDispatcher
    ) {
        return List.of();
    }

    protected Map<String, TaskHandler<?>> getTaskHandlerResolverMap() {
        return Map.of();
    }

    protected WorkflowMapper getWorkflowMapper() {
        return new JSONWorkflowMapper();
    }

    protected Job startJob(String workflowId, Map<String, ?> inputs) {
        Coordinator coordinator = new Coordinator();

        SyncMessageBroker messageBroker = new SyncMessageBroker();

        messageBroker.receive(Queues.COMPLETIONS, o -> coordinator.complete((TaskExecution) o));
        messageBroker.receive(Queues.JOBS, o -> coordinator.start((Job) o));
        messageBroker.receive(
            Queues.ERRORS,
            message -> {
                TaskExecution erringTask = (TaskExecution) message;

                Error error = erringTask.getError();

                logger.error(error.getMessage());
            }
        );

        coordinator.setMessageBroker(messageBroker);

        TaskHandlerResolverChain taskHandlerResolverChain = new TaskHandlerResolverChain();

        taskHandlerResolverChain.setTaskHandlerResolvers(
            List.of(new DefaultTaskHandlerResolver(getTaskHandlerResolverMap()))
        );

        Worker worker = WorkerImpl
            .builder()
            .withTaskHandlerResolver(taskHandlerResolverChain)
            .withMessageBroker(messageBroker)
            .withEventPublisher(e -> {})
            .withTaskEvaluator(SpelTaskEvaluator.create())
            .build();

        coordinator.setContextRepository(contextRepository);
        coordinator.setJobRepository(jobRepository);
        coordinator.setWorkflowRepository(new ResourceBasedWorkflowRepository(getWorkflowMapper()));
        coordinator.setJobTaskRepository(taskExecutionRepository);

        SyncMessageBroker coordinatorMessageBroker = new SyncMessageBroker();

        coordinatorMessageBroker.receive(Queues.TASKS, o -> worker.handle((TaskExecution) o));

        TaskDispatcherChain taskDispatcherChain = new TaskDispatcherChain();

        taskDispatcherChain.setResolvers(
            Stream
                .concat(
                    getTaskDispatcherResolvers(coordinatorMessageBroker, taskDispatcherChain).stream(),
                    Stream.of(new DefaultTaskDispatcher(coordinatorMessageBroker))
                )
                .toList()
        );

        coordinator.setErrorHandler(getJobTaskErrorHandler(taskDispatcherChain));
        coordinator.setEventPublisher(e -> {});
        coordinator.setTaskDispatcher(taskDispatcherChain);

        DefaultJobExecutor jobExecutor = new DefaultJobExecutor();

        jobExecutor.setContextRepository(contextRepository);
        jobExecutor.setJobTaskRepository(taskExecutionRepository);
        jobExecutor.setWorkflowRepository(new ResourceBasedWorkflowRepository(getWorkflowMapper()));
        jobExecutor.setTaskDispatcher(taskDispatcherChain);
        jobExecutor.setTaskEvaluator(SpelTaskEvaluator.create());

        coordinator.setJobExecutor(jobExecutor);

        DefaultTaskCompletionHandler defaultTaskCompletionHandler = new DefaultTaskCompletionHandler();

        defaultTaskCompletionHandler.setContextRepository(contextRepository);
        defaultTaskCompletionHandler.setJobExecutor(jobExecutor);
        defaultTaskCompletionHandler.setJobRepository(jobRepository);
        defaultTaskCompletionHandler.setJobTaskRepository(taskExecutionRepository);
        defaultTaskCompletionHandler.setWorkflowRepository(new ResourceBasedWorkflowRepository(getWorkflowMapper()));
        defaultTaskCompletionHandler.setEventPublisher(e -> {});
        defaultTaskCompletionHandler.setTaskEvaluator(SpelTaskEvaluator.create());

        TaskCompletionHandlerChain taskCompletionHandlerChain = new TaskCompletionHandlerChain();

        taskCompletionHandlerChain.setTaskCompletionHandlers(
            Stream
                .concat(
                    getTaskCompletionHandlers(taskCompletionHandlerChain, taskDispatcherChain).stream(),
                    Stream.of(defaultTaskCompletionHandler)
                )
                .toList()
        );

        coordinator.setTaskCompletionHandler(taskCompletionHandlerChain);

        Job job = coordinator.create(MapObject.of(Map.of("workflowId", workflowId, "inputs", inputs)));

        return jobRepository.getById(job.getId());
    }

    private TaskExecutionErrorHandler getJobTaskErrorHandler(TaskDispatcher taskDispatcher) {
        TaskExecutionErrorHandler jobTaskErrorHandler = new TaskExecutionErrorHandler();

        jobTaskErrorHandler.setJobRepository(jobRepository);
        jobTaskErrorHandler.setJobTaskRepository(taskExecutionRepository);
        jobTaskErrorHandler.setTaskDispatcher(taskDispatcher);
        jobTaskErrorHandler.setEventPublisher(e -> {});

        return jobTaskErrorHandler;
    }
}
