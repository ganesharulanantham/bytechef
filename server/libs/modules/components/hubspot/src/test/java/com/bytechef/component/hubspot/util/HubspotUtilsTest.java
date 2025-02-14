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

package com.bytechef.component.hubspot.util;

import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.hubspot.constant.HubspotConstants.EVENT_TYPE;
import static com.bytechef.component.hubspot.constant.HubspotConstants.HAPIKEY;
import static com.bytechef.component.hubspot.constant.HubspotConstants.ID;
import static com.bytechef.component.hubspot.constant.HubspotConstants.LABEL;
import static com.bytechef.component.hubspot.constant.HubspotConstants.RESULTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Option;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TriggerContext;
import com.bytechef.component.definition.TriggerDefinition.WebhookBody;
import com.bytechef.component.definition.TypeReference;
import com.bytechef.component.test.definition.MockParametersFactory;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Monika Kušter
 */
class HubspotUtilsTest {

    private final ArgumentCaptor<Http.Body> bodyArgumentCaptor = ArgumentCaptor.forClass(Http.Body.class);
    private final ActionContext mockedActionContext = mock(ActionContext.class);
    private final TriggerContext mockedTriggerContext = mock(TriggerContext.class);
    private final Http.Executor mockedExecutor = mock(Http.Executor.class);
    private final Parameters mockedParameters =
        MockParametersFactory.create(Map.of("__item", Map.of("properties", Map.of("pipeline", "123"))));
    private final Http.Response mockedResponse = mock(Http.Response.class);
    private final WebhookBody mockedWebhookBody = mock(WebhookBody.class);
    private final ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void testExtractFirstContentMap() {
        List<Map<String, Object>> contentList = List.of(Map.of("key1", "value1"), Map.of("key2", "value2"));

        when(mockedWebhookBody.getContent(any(TypeReference.class)))
            .thenReturn(contentList);

        Map<String, Object> result = HubspotUtils.extractFirstContentMap(mockedWebhookBody);

        assertEquals("value1", result.get("key1"));
    }

    @Test
    void testGetContactsOptions() {
        Map<String, Object> propertiesMap = Map.of("firstname", "first", "lastname", "last");

        mockHttpResponse();
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(RESULTS, List.of(Map.of(ID, "123", "properties", propertiesMap))));

        List<Option<String>> expectedOptions = List.of(option("first last", "123"));

        assertEquals(
            expectedOptions,
            HubspotUtils.getContactsOptions(mockedParameters, mockedParameters, Map.of(), "", mockedActionContext));
    }

    @Test
    void testGetDealStageOptions() {
        mockHttpResponse();
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(RESULTS, List.of(Map.of(ID, "123", LABEL, "label"))));

        List<Option<String>> expectedOptions = List.of(option("label", "123"));

        assertEquals(
            expectedOptions,
            HubspotUtils.getDealStageOptions(mockedParameters, mockedParameters, Map.of(), "", mockedActionContext));
    }

    @Test
    void testGetOwnerOptions() {
        mockHttpResponse();
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(RESULTS, List.of(Map.of(ID, "123", "email", "label"))));

        List<Option<String>> expectedOptions = List.of(option("label", "123"));

        assertEquals(
            expectedOptions,
            HubspotUtils.getOwnerOptions(mockedParameters, mockedParameters, Map.of(), "", mockedActionContext));
    }

    @Test
    void testGetPipelineDealOptions() {
        mockHttpResponse();
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(RESULTS, List.of(Map.of(ID, "123", LABEL, "label"))));

        List<Option<String>> expectedOptions = List.of(option("label", "123"));

        assertEquals(
            expectedOptions,
            HubspotUtils.getPipelineDealOptions(mockedParameters, mockedParameters, Map.of(), "", mockedActionContext));
    }

    @Test
    void testGetTicketIdOptions() {
        Map<String, Object> propertiesMap = Map.of("subject", "ticket");

        mockHttpResponse();

        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(RESULTS, List.of(Map.of(ID, "123", "properties", propertiesMap))));

        List<Option<String>> expectedOptions = List.of(option("ticket", "123"));

        assertEquals(
            expectedOptions,
            HubspotUtils.getTicketIdOptions(mockedParameters, mockedParameters, Map.of(), "", mockedActionContext));
    }

    private void mockHttpResponse() {
        when(mockedActionContext.http(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.configuration(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.execute())
            .thenReturn(mockedResponse);
    }

    @Test
    void testSubscribeWebhook() {
        when(mockedTriggerContext.http(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.queryParameter(stringArgumentCaptor.capture(), stringArgumentCaptor.capture()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.body(bodyArgumentCaptor.capture()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.configuration(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.execute())
            .thenReturn(mockedResponse);
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of(ID, "abc"));

        String result = HubspotUtils.subscribeWebhook(
            "eventType", "appId", "hubspot key", "webhookUrl", mockedTriggerContext);

        assertEquals("abc", result);

        assertEquals(List.of(HAPIKEY, "hubspot key", HAPIKEY, "hubspot key"), stringArgumentCaptor.getAllValues());

        List<Object> contents = bodyArgumentCaptor.getAllValues()
            .stream()
            .map(Http.Body::getContent)
            .toList();

        assertEquals(List.of(
            Map.of("throttling", Map.of(
                "period", "SECONDLY",
                "maxConcurrentRequests", 10),
                "targetUrl", "webhookUrl"),
            Map.of(EVENT_TYPE, "eventType", "active", true)),
            contents);
    }

    @Test
    void testUnsubscribeWebhook() {
        when(mockedTriggerContext.http(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.queryParameter(stringArgumentCaptor.capture(), stringArgumentCaptor.capture()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.configuration(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.execute())
            .thenReturn(mockedResponse);

        HubspotUtils.unsubscribeWebhook("appId", "subscriptionId", "hubspot key", mockedTriggerContext);

        assertEquals(List.of(HAPIKEY, "hubspot key"), stringArgumentCaptor.getAllValues());
    }
}
