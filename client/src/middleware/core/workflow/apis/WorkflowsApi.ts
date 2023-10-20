/* tslint:disable */
/* eslint-disable */
/**
 * Core Workflow API
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
  WorkflowModel,
  WorkflowResponseModel,
} from '../models';
import {
    WorkflowModelFromJSON,
    WorkflowModelToJSON,
    WorkflowResponseModelFromJSON,
    WorkflowResponseModelToJSON,
} from '../models';

export interface CreateWorkflowRequest {
    workflowModel: WorkflowModel;
}

export interface DeleteWorkflowRequest {
    id: string;
}

export interface GetWorkflowRequest {
    id: string;
}

export interface TestWorkflowRequest {
    id: string;
    requestBody: { [key: string]: object; };
}

export interface UpdateWorkflowRequest {
    id: string;
    workflowModel: WorkflowModel;
}

/**
 * 
 */
export class WorkflowsApi extends runtime.BaseAPI {

    /**
     * Create a new workflow.
     * Create a new workflow
     */
    async createWorkflowRaw(requestParameters: CreateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowModel>> {
        if (requestParameters.workflowModel === null || requestParameters.workflowModel === undefined) {
            throw new runtime.RequiredError('workflowModel','Required parameter requestParameters.workflowModel was null or undefined when calling createWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workflows`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: WorkflowModelToJSON(requestParameters.workflowModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowModelFromJSON(jsonValue));
    }

    /**
     * Create a new workflow.
     * Create a new workflow
     */
    async createWorkflow(requestParameters: CreateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowModel> {
        const response = await this.createWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete a workflow.
     * Delete a workflow
     */
    async deleteWorkflowRaw(requestParameters: DeleteWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling deleteWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Delete a workflow.
     * Delete a workflow
     */
    async deleteWorkflow(requestParameters: DeleteWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.deleteWorkflowRaw(requestParameters, initOverrides);
    }

    /**
     * Get a workflow by id.
     * Get a workflow by id
     */
    async getWorkflowRaw(requestParameters: GetWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling getWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowModelFromJSON(jsonValue));
    }

    /**
     * Get a workflow by id.
     * Get a workflow by id
     */
    async getWorkflow(requestParameters: GetWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowModel> {
        const response = await this.getWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workflow definitions.
     * Get workflow definitions
     */
    async getWorkflowsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<WorkflowModel>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkflowModelFromJSON));
    }

    /**
     * Get workflow definitions.
     * Get workflow definitions
     */
    async getWorkflows(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<WorkflowModel>> {
        const response = await this.getWorkflowsRaw(initOverrides);
        return await response.value();
    }

    /**
     * Execute a workflow synchronously for testing purposes.
     * Execute a workflow synchronously for testing purpose
     */
    async testWorkflowRaw(requestParameters: TestWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowResponseModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling testWorkflow.');
        }

        if (requestParameters.requestBody === null || requestParameters.requestBody === undefined) {
            throw new runtime.RequiredError('requestBody','Required parameter requestParameters.requestBody was null or undefined when calling testWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workflows/{id}/tests`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: requestParameters.requestBody,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowResponseModelFromJSON(jsonValue));
    }

    /**
     * Execute a workflow synchronously for testing purposes.
     * Execute a workflow synchronously for testing purpose
     */
    async testWorkflow(requestParameters: TestWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowResponseModel> {
        const response = await this.testWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Update an existing workflow.
     * Update an existing workflow
     */
    async updateWorkflowRaw(requestParameters: UpdateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateWorkflow.');
        }

        if (requestParameters.workflowModel === null || requestParameters.workflowModel === undefined) {
            throw new runtime.RequiredError('workflowModel','Required parameter requestParameters.workflowModel was null or undefined when calling updateWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: WorkflowModelToJSON(requestParameters.workflowModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowModelFromJSON(jsonValue));
    }

    /**
     * Update an existing workflow.
     * Update an existing workflow
     */
    async updateWorkflow(requestParameters: UpdateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowModel> {
        const response = await this.updateWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
