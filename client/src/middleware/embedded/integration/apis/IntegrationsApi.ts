/* tslint:disable */
/* eslint-disable */
/**
 * Embedded Integration API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  CreateIntegrationWorkflowRequestModel,
  IntegrationModel,
  UpdateTagsRequestModel,
  WorkflowModel,
} from '../models';
import {
    CreateIntegrationWorkflowRequestModelFromJSON,
    CreateIntegrationWorkflowRequestModelToJSON,
    IntegrationModelFromJSON,
    IntegrationModelToJSON,
    UpdateTagsRequestModelFromJSON,
    UpdateTagsRequestModelToJSON,
    WorkflowModelFromJSON,
    WorkflowModelToJSON,
} from '../models';

export interface CreateIntegrationRequest {
    integrationModel: IntegrationModel;
}

export interface CreateIntegrationWorkflowRequest {
    id: number;
    createIntegrationWorkflowRequestModel: CreateIntegrationWorkflowRequestModel;
}

export interface DeleteIntegrationRequest {
    id: number;
}

export interface GetIntegrationRequest {
    id: number;
}

export interface GetIntegrationWorkflowsRequest {
    id: number;
}

export interface GetIntegrationsRequest {
    categoryIds?: Array<number>;
    tagIds?: Array<number>;
}

export interface UpdateIntegrationRequest {
    id: number;
    integrationModel: IntegrationModel;
}

export interface UpdateIntegrationTagsRequest {
    id: number;
    updateTagsRequestModel: UpdateTagsRequestModel;
}

/**
 * 
 */
export class IntegrationsApi extends runtime.BaseAPI {

    /**
     * Create a new integration.
     * Create a new integration
     */
    async createIntegrationRaw(requestParameters: CreateIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<IntegrationModel>> {
        if (requestParameters.integrationModel === null || requestParameters.integrationModel === undefined) {
            throw new runtime.RequiredError('integrationModel','Required parameter requestParameters.integrationModel was null or undefined when calling createIntegration.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/integrations`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: IntegrationModelToJSON(requestParameters.integrationModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => IntegrationModelFromJSON(jsonValue));
    }

    /**
     * Create a new integration.
     * Create a new integration
     */
    async createIntegration(requestParameters: CreateIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<IntegrationModel> {
        const response = await this.createIntegrationRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Create new workflow and adds it to an existing integration.
     * Create new workflow and adds it to an existing integration
     */
    async createIntegrationWorkflowRaw(requestParameters: CreateIntegrationWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling createIntegrationWorkflow.');
        }

        if (requestParameters.createIntegrationWorkflowRequestModel === null || requestParameters.createIntegrationWorkflowRequestModel === undefined) {
            throw new runtime.RequiredError('createIntegrationWorkflowRequestModel','Required parameter requestParameters.createIntegrationWorkflowRequestModel was null or undefined when calling createIntegrationWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/integrations/{id}/workflows`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: CreateIntegrationWorkflowRequestModelToJSON(requestParameters.createIntegrationWorkflowRequestModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowModelFromJSON(jsonValue));
    }

    /**
     * Create new workflow and adds it to an existing integration.
     * Create new workflow and adds it to an existing integration
     */
    async createIntegrationWorkflow(requestParameters: CreateIntegrationWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowModel> {
        const response = await this.createIntegrationWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete an integration.
     * Delete an integration
     */
    async deleteIntegrationRaw(requestParameters: DeleteIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling deleteIntegration.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/integrations/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Delete an integration.
     * Delete an integration
     */
    async deleteIntegration(requestParameters: DeleteIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.deleteIntegrationRaw(requestParameters, initOverrides);
    }

    /**
     * Get an integration by id.
     * Get an integration by id
     */
    async getIntegrationRaw(requestParameters: GetIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<IntegrationModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling getIntegration.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/integrations/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => IntegrationModelFromJSON(jsonValue));
    }

    /**
     * Get an integration by id.
     * Get an integration by id
     */
    async getIntegration(requestParameters: GetIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<IntegrationModel> {
        const response = await this.getIntegrationRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workflows for particular integration.
     * Get workflows for particular integration
     */
    async getIntegrationWorkflowsRaw(requestParameters: GetIntegrationWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<WorkflowModel>>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling getIntegrationWorkflows.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/integrations/{id}/workflows`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkflowModelFromJSON));
    }

    /**
     * Get workflows for particular integration.
     * Get workflows for particular integration
     */
    async getIntegrationWorkflows(requestParameters: GetIntegrationWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<WorkflowModel>> {
        const response = await this.getIntegrationWorkflowsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get integrations.
     * Get integrations
     */
    async getIntegrationsRaw(requestParameters: GetIntegrationsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<IntegrationModel>>> {
        const queryParameters: any = {};

        if (requestParameters.categoryIds) {
            queryParameters['categoryIds'] = requestParameters.categoryIds;
        }

        if (requestParameters.tagIds) {
            queryParameters['tagIds'] = requestParameters.tagIds;
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/integrations`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(IntegrationModelFromJSON));
    }

    /**
     * Get integrations.
     * Get integrations
     */
    async getIntegrations(requestParameters: GetIntegrationsRequest = {}, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<IntegrationModel>> {
        const response = await this.getIntegrationsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Update an existing integration.
     * Update an existing integration
     */
    async updateIntegrationRaw(requestParameters: UpdateIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<IntegrationModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateIntegration.');
        }

        if (requestParameters.integrationModel === null || requestParameters.integrationModel === undefined) {
            throw new runtime.RequiredError('integrationModel','Required parameter requestParameters.integrationModel was null or undefined when calling updateIntegration.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/integrations/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: IntegrationModelToJSON(requestParameters.integrationModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => IntegrationModelFromJSON(jsonValue));
    }

    /**
     * Update an existing integration.
     * Update an existing integration
     */
    async updateIntegration(requestParameters: UpdateIntegrationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<IntegrationModel> {
        const response = await this.updateIntegrationRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Updates tags of an existing integration.
     * Updates tags of an existing integration
     */
    async updateIntegrationTagsRaw(requestParameters: UpdateIntegrationTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateIntegrationTags.');
        }

        if (requestParameters.updateTagsRequestModel === null || requestParameters.updateTagsRequestModel === undefined) {
            throw new runtime.RequiredError('updateTagsRequestModel','Required parameter requestParameters.updateTagsRequestModel was null or undefined when calling updateIntegrationTags.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/integrations/{id}/tags`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: UpdateTagsRequestModelToJSON(requestParameters.updateTagsRequestModel),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Updates tags of an existing integration.
     * Updates tags of an existing integration
     */
    async updateIntegrationTags(requestParameters: UpdateIntegrationTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateIntegrationTagsRaw(requestParameters, initOverrides);
    }

}
