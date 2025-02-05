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

package com.bytechef.component.google.tasks.action;

import static com.bytechef.component.google.tasks.constant.GoogleTasksConstants.SHOW_COMPLETED;
import static com.bytechef.component.google.tasks.constant.GoogleTasksConstants.TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.Parameters;
import com.bytechef.component.test.definition.MockParametersFactory;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Marija Horvat
 */
public class GoogleTasksListTasksActionTest extends AbstractGoogleTasksActionTest {

    private final Parameters mockedParameters =
        MockParametersFactory.create(Map.of(TITLE, "test", SHOW_COMPLETED, true));
    private final ArgumentCaptor<String> queryKeyCaptor = ArgumentCaptor.forClass(String.class);
    private final ArgumentCaptor<Object> queryValueCaptor = ArgumentCaptor.forClass(Object.class);

    @Test
    void testPerform() {
        when(mockedExecutor.queryParameters(queryKeyCaptor.capture(), queryValueCaptor.capture()))
            .thenReturn(mockedExecutor);

        Object result = GoogleTasksListTasksAction.perform(mockedParameters, mockedParameters, mockedContext);

        assertEquals(responseMap, result);

        assertEquals(SHOW_COMPLETED, queryKeyCaptor.getValue());
        assertEquals(mockedParameters.getRequiredBoolean(SHOW_COMPLETED), queryValueCaptor.getValue());
    }
}
