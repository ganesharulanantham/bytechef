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

package com.bytechef.component.data.stream;

import static com.bytechef.component.definition.ComponentDSL.component;
import static com.bytechef.platform.component.definition.DataStreamComponentDefinition.DATA_STREAM;

import com.bytechef.component.ComponentDefinitionFactory;
import com.bytechef.component.data.stream.action.DataStreamSyncAction;
import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.platform.component.definition.AbstractComponentDefinitionWrapper;
import com.bytechef.platform.component.definition.DataStreamComponentDefinition;
import org.springframework.stereotype.Component;

/**
 * @author Ivica Cardic
 */
@Component(DATA_STREAM + "_v1_ComponentDefinitionFactory")
public class DataStreamComponentDefinitionFactory implements ComponentDefinitionFactory {

    private static final DataStreamComponentDefinition COMPONENT_DEFINITION = new DataStreamComponentDefinitionImpl(
        component(DATA_STREAM)
            .title("Data Stream")
            .description("With the Data Stream, you can transfer large amounts of data efficiently.")
            .icon("path:assets/data-stream.svg")
            .actions(DataStreamSyncAction.ACTION_DEFINITION)
            .workflowConnectionKeys("source", "destination"));

    @Override
    public ComponentDefinition getDefinition() {
        return COMPONENT_DEFINITION;
    }

    private static class DataStreamComponentDefinitionImpl extends AbstractComponentDefinitionWrapper
        implements DataStreamComponentDefinition {

        public DataStreamComponentDefinitionImpl(ComponentDefinition componentDefinition) {
            super(componentDefinition);
        }
    }
}
