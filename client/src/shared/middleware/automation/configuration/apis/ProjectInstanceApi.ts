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
  CreateProjectInstanceWorkflowJob200Response,
  Environment,
  ProjectInstance,
  ProjectInstanceWorkflow,
} from '../models/index';
import {
    CreateProjectInstanceWorkflowJob200ResponseFromJSON,
    CreateProjectInstanceWorkflowJob200ResponseToJSON,
    EnvironmentFromJSON,
    EnvironmentToJSON,
    ProjectInstanceFromJSON,
    ProjectInstanceToJSON,
    ProjectInstanceWorkflowFromJSON,
    ProjectInstanceWorkflowToJSON,
} from '../models/index';

export interface CreateProjectInstanceRequest {
    projectInstance: ProjectInstance;
}

export interface CreateProjectInstanceWorkflowJobRequest {
    id: number;
    workflowId: string;
}

export interface DeleteProjectInstanceRequest {
    id: number;
}

export interface EnableProjectInstanceRequest {
    id: number;
    enable: boolean;
}

export interface EnableProjectInstanceWorkflowRequest {
    id: number;
    workflowId: string;
    enable: boolean;
}

export interface GetProjectInstanceRequest {
    id: number;
}

export interface GetWorkspaceProjectInstancesRequest {
    id: number;
    environment?: Environment;
    projectId?: number;
    tagId?: number;
    includeAllFields?: boolean;
}

export interface UpdateProjectInstanceRequest {
    id: number;
    projectInstance: ProjectInstance;
}

export interface UpdateProjectInstanceWorkflowRequest {
    id: number;
    projectInstanceWorkflowId: number;
    projectInstanceWorkflow: Omit<ProjectInstanceWorkflow, 'createdBy'|'createdDate'|'id'|'lastModifiedBy'|'lastModifiedDate'|'workflowReferenceCode'>;
}

/**
 * 
 */
export class ProjectInstanceApi extends runtime.BaseAPI {

    /**
     * Create a new project instance.
     * Create a new project instance
     */
    async createProjectInstanceRaw(requestParameters: CreateProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<number>> {
        if (requestParameters['projectInstance'] == null) {
            throw new runtime.RequiredError(
                'projectInstance',
                'Required parameter "projectInstance" was null or undefined when calling createProjectInstance().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/project-instances`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ProjectInstanceToJSON(requestParameters['projectInstance']),
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<number>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     * Create a new project instance.
     * Create a new project instance
     */
    async createProjectInstance(requestParameters: CreateProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<number> {
        const response = await this.createProjectInstanceRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Create a request for running a new job.
     * Create a request for running a new job
     */
    async createProjectInstanceWorkflowJobRaw(requestParameters: CreateProjectInstanceWorkflowJobRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<CreateProjectInstanceWorkflowJob200Response>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling createProjectInstanceWorkflowJob().'
            );
        }

        if (requestParameters['workflowId'] == null) {
            throw new runtime.RequiredError(
                'workflowId',
                'Required parameter "workflowId" was null or undefined when calling createProjectInstanceWorkflowJob().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}/workflows/{workflowId}/jobs`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters['workflowId']))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => CreateProjectInstanceWorkflowJob200ResponseFromJSON(jsonValue));
    }

    /**
     * Create a request for running a new job.
     * Create a request for running a new job
     */
    async createProjectInstanceWorkflowJob(requestParameters: CreateProjectInstanceWorkflowJobRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<CreateProjectInstanceWorkflowJob200Response> {
        const response = await this.createProjectInstanceWorkflowJobRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete a project instance.
     * Delete a project instance
     */
    async deleteProjectInstanceRaw(requestParameters: DeleteProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling deleteProjectInstance().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Delete a project instance.
     * Delete a project instance
     */
    async deleteProjectInstance(requestParameters: DeleteProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.deleteProjectInstanceRaw(requestParameters, initOverrides);
    }

    /**
     * Enable/disable a project instance.
     * Enable/disable a project instance
     */
    async enableProjectInstanceRaw(requestParameters: EnableProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling enableProjectInstance().'
            );
        }

        if (requestParameters['enable'] == null) {
            throw new runtime.RequiredError(
                'enable',
                'Required parameter "enable" was null or undefined when calling enableProjectInstance().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}/enable/{enable}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"enable"}}`, encodeURIComponent(String(requestParameters['enable']))),
            method: 'PATCH',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Enable/disable a project instance.
     * Enable/disable a project instance
     */
    async enableProjectInstance(requestParameters: EnableProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.enableProjectInstanceRaw(requestParameters, initOverrides);
    }

    /**
     * Enable/disable a workflow of a project instance.
     * Enable/disable a workflow of a project instance
     */
    async enableProjectInstanceWorkflowRaw(requestParameters: EnableProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling enableProjectInstanceWorkflow().'
            );
        }

        if (requestParameters['workflowId'] == null) {
            throw new runtime.RequiredError(
                'workflowId',
                'Required parameter "workflowId" was null or undefined when calling enableProjectInstanceWorkflow().'
            );
        }

        if (requestParameters['enable'] == null) {
            throw new runtime.RequiredError(
                'enable',
                'Required parameter "enable" was null or undefined when calling enableProjectInstanceWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}/workflows/{workflowId}/enable/{enable}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters['workflowId']))).replace(`{${"enable"}}`, encodeURIComponent(String(requestParameters['enable']))),
            method: 'PATCH',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Enable/disable a workflow of a project instance.
     * Enable/disable a workflow of a project instance
     */
    async enableProjectInstanceWorkflow(requestParameters: EnableProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.enableProjectInstanceWorkflowRaw(requestParameters, initOverrides);
    }

    /**
     * Get a project instance by id.
     * Get a project instance by id
     */
    async getProjectInstanceRaw(requestParameters: GetProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ProjectInstance>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getProjectInstance().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ProjectInstanceFromJSON(jsonValue));
    }

    /**
     * Get a project instance by id.
     * Get a project instance by id
     */
    async getProjectInstance(requestParameters: GetProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ProjectInstance> {
        const response = await this.getProjectInstanceRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get project instances.
     * Get project instances
     */
    async getWorkspaceProjectInstancesRaw(requestParameters: GetWorkspaceProjectInstancesRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<ProjectInstance>>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getWorkspaceProjectInstances().'
            );
        }

        const queryParameters: any = {};

        if (requestParameters['environment'] != null) {
            queryParameters['environment'] = requestParameters['environment'];
        }

        if (requestParameters['projectId'] != null) {
            queryParameters['projectId'] = requestParameters['projectId'];
        }

        if (requestParameters['tagId'] != null) {
            queryParameters['tagId'] = requestParameters['tagId'];
        }

        if (requestParameters['includeAllFields'] != null) {
            queryParameters['includeAllFields'] = requestParameters['includeAllFields'];
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workspaces/{id}/project-instances`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(ProjectInstanceFromJSON));
    }

    /**
     * Get project instances.
     * Get project instances
     */
    async getWorkspaceProjectInstances(requestParameters: GetWorkspaceProjectInstancesRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<ProjectInstance>> {
        const response = await this.getWorkspaceProjectInstancesRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Update an existing project instance.
     * Update an existing project instance
     */
    async updateProjectInstanceRaw(requestParameters: UpdateProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling updateProjectInstance().'
            );
        }

        if (requestParameters['projectInstance'] == null) {
            throw new runtime.RequiredError(
                'projectInstance',
                'Required parameter "projectInstance" was null or undefined when calling updateProjectInstance().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/project-instances/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: ProjectInstanceToJSON(requestParameters['projectInstance']),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Update an existing project instance.
     * Update an existing project instance
     */
    async updateProjectInstance(requestParameters: UpdateProjectInstanceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateProjectInstanceRaw(requestParameters, initOverrides);
    }

    /**
     * Update an existing project instance workflow.
     * Update an existing project instance workflow
     */
    async updateProjectInstanceWorkflowRaw(requestParameters: UpdateProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling updateProjectInstanceWorkflow().'
            );
        }

        if (requestParameters['projectInstanceWorkflowId'] == null) {
            throw new runtime.RequiredError(
                'projectInstanceWorkflowId',
                'Required parameter "projectInstanceWorkflowId" was null or undefined when calling updateProjectInstanceWorkflow().'
            );
        }

        if (requestParameters['projectInstanceWorkflow'] == null) {
            throw new runtime.RequiredError(
                'projectInstanceWorkflow',
                'Required parameter "projectInstanceWorkflow" was null or undefined when calling updateProjectInstanceWorkflow().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/project-instances/{id}/project-instance-workflows/{projectInstanceWorkflowId}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))).replace(`{${"projectInstanceWorkflowId"}}`, encodeURIComponent(String(requestParameters['projectInstanceWorkflowId']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: ProjectInstanceWorkflowToJSON(requestParameters['projectInstanceWorkflow']),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Update an existing project instance workflow.
     * Update an existing project instance workflow
     */
    async updateProjectInstanceWorkflow(requestParameters: UpdateProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateProjectInstanceWorkflowRaw(requestParameters, initOverrides);
    }

}
