/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Configuration Internal API
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
  Workflow,
} from '../models/index';
import {
    WorkflowFromJSON,
    WorkflowToJSON,
} from '../models/index';

export interface CreateProjectWorkflowRequest {
    id: number;
    workflow: Workflow;
}

export interface DeleteWorkflowRequest {
    id: string;
}

export interface DuplicateWorkflowRequest {
    id: number;
    workflowId: string;
}

export interface GetProjectVersionWorkflowsRequest {
    id: number;
    projectVersion: number;
    includeAllFields?: boolean;
}

export interface GetProjectWorkflowRequest {
    projectWorkflowId: number;
}

export interface GetProjectWorkflowsRequest {
    id: number;
}

export interface GetWorkflowRequest {
    id: string;
}

export interface UpdateWorkflowRequest {
    id: string;
    workflow: Workflow;
}

/**
 * 
 */
export class WorkflowApi extends runtime.BaseAPI {

    /**
     * Create new workflow and adds it to an existing project.
     * Create new workflow and adds it to an existing project.
     */
    async createProjectWorkflowRaw(requestParameters: CreateProjectWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<number>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling createProjectWorkflow().'
            );
        }

        if (requestParameters['workflow'] == null) {
            throw new runtime.RequiredError(
                'workflow',
                'Required parameter "workflow" was null or undefined when calling createProjectWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/projects/{id}/workflows`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: WorkflowToJSON(requestParameters['workflow']),
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<number>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     * Create new workflow and adds it to an existing project.
     * Create new workflow and adds it to an existing project.
     */
    async createProjectWorkflow(requestParameters: CreateProjectWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<number> {
        const response = await this.createProjectWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete a workflow.
     * Delete a workflow
     */
    async deleteWorkflowRaw(requestParameters: DeleteWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling deleteWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
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
     * Duplicates existing workflow.
     * Duplicates existing workflow.
     */
    async duplicateWorkflowRaw(requestParameters: DuplicateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<string>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling duplicateWorkflow().'
            );
        }

        if (requestParameters['workflowId'] == null) {
            throw new runtime.RequiredError(
                'workflowId',
                'Required parameter "workflowId" was null or undefined when calling duplicateWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/projects/{id}/workflows/{workflowId}/duplicate`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters['workflowId']))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<string>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     * Duplicates existing workflow.
     * Duplicates existing workflow.
     */
    async duplicateWorkflow(requestParameters: DuplicateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<string> {
        const response = await this.duplicateWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workflows for particular project version.
     * Get workflows for particular project version.
     */
    async getProjectVersionWorkflowsRaw(requestParameters: GetProjectVersionWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<Workflow>>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getProjectVersionWorkflows().'
            );
        }

        if (requestParameters['projectVersion'] == null) {
            throw new runtime.RequiredError(
                'projectVersion',
                'Required parameter "projectVersion" was null or undefined when calling getProjectVersionWorkflows().'
            );
        }

        const queryParameters: any = {};

        if (requestParameters['includeAllFields'] != null) {
            queryParameters['includeAllFields'] = requestParameters['includeAllFields'];
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/projects/{id}/versions/{projectVersion}/workflows`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"projectVersion"}}`, encodeURIComponent(String(requestParameters['projectVersion']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkflowFromJSON));
    }

    /**
     * Get workflows for particular project version.
     * Get workflows for particular project version.
     */
    async getProjectVersionWorkflows(requestParameters: GetProjectVersionWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<Workflow>> {
        const response = await this.getProjectVersionWorkflowsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workflow for particular project.
     * Get workflow for particular project.
     */
    async getProjectWorkflowRaw(requestParameters: GetProjectWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Workflow>> {
        if (requestParameters['projectWorkflowId'] == null) {
            throw new runtime.RequiredError(
                'projectWorkflowId',
                'Required parameter "projectWorkflowId" was null or undefined when calling getProjectWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows/by-project-workflow-id/{projectWorkflowId}`.replace(`{${"projectWorkflowId"}}`, encodeURIComponent(String(requestParameters['projectWorkflowId']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowFromJSON(jsonValue));
    }

    /**
     * Get workflow for particular project.
     * Get workflow for particular project.
     */
    async getProjectWorkflow(requestParameters: GetProjectWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Workflow> {
        const response = await this.getProjectWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workflows for particular project.
     * Get workflows for particular project.
     */
    async getProjectWorkflowsRaw(requestParameters: GetProjectWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<Workflow>>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getProjectWorkflows().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/projects/{id}/workflows`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkflowFromJSON));
    }

    /**
     * Get workflows for particular project.
     * Get workflows for particular project.
     */
    async getProjectWorkflows(requestParameters: GetProjectWorkflowsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<Workflow>> {
        const response = await this.getProjectWorkflowsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get a workflow by id.
     * Get a workflow by id
     */
    async getWorkflowRaw(requestParameters: GetWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Workflow>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowFromJSON(jsonValue));
    }

    /**
     * Get a workflow by id.
     * Get a workflow by id
     */
    async getWorkflow(requestParameters: GetWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Workflow> {
        const response = await this.getWorkflowRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Update an existing workflow.
     * Update an existing workflow
     */
    async updateWorkflowRaw(requestParameters: UpdateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling updateWorkflow().'
            );
        }

        if (requestParameters['workflow'] == null) {
            throw new runtime.RequiredError(
                'workflow',
                'Required parameter "workflow" was null or undefined when calling updateWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workflows/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: WorkflowToJSON(requestParameters['workflow']),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Update an existing workflow.
     * Update an existing workflow
     */
    async updateWorkflow(requestParameters: UpdateWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateWorkflowRaw(requestParameters, initOverrides);
    }

}
