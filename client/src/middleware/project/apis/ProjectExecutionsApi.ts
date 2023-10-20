/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  PageModel,
} from '../models';
import {
    PageModelFromJSON,
    PageModelToJSON,
} from '../models';

export interface GetProjectExecutionsRequest {
    jobStatus?: GetProjectExecutionsJobStatusEnum;
    jobStartTime?: Date;
    jobEndTime?: Date;
    projectId?: number;
    projectInstanceId?: number;
    workflowId?: string;
    pageNumber?: number;
}

/**
 * 
 */
export class ProjectExecutionsApi extends runtime.BaseAPI {

    /**
     * Get project executions.
     * Get project executions.
     */
    async getProjectExecutionsRaw(requestParameters: GetProjectExecutionsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<PageModel>> {
        const queryParameters: any = {};

        if (requestParameters.jobStatus !== undefined) {
            queryParameters['jobStatus'] = requestParameters.jobStatus;
        }

        if (requestParameters.jobStartTime !== undefined) {
            queryParameters['jobStartTime'] = (requestParameters.jobStartTime as any).toISOString();
        }

        if (requestParameters.jobEndTime !== undefined) {
            queryParameters['jobEndTime'] = (requestParameters.jobEndTime as any).toISOString();
        }

        if (requestParameters.projectId !== undefined) {
            queryParameters['projectId'] = requestParameters.projectId;
        }

        if (requestParameters.projectInstanceId !== undefined) {
            queryParameters['projectInstanceId'] = requestParameters.projectInstanceId;
        }

        if (requestParameters.workflowId !== undefined) {
            queryParameters['workflowId'] = requestParameters.workflowId;
        }

        if (requestParameters.pageNumber !== undefined) {
            queryParameters['pageNumber'] = requestParameters.pageNumber;
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-executions`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => PageModelFromJSON(jsonValue));
    }

    /**
     * Get project executions.
     * Get project executions.
     */
    async getProjectExecutions(requestParameters: GetProjectExecutionsRequest = {}, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<PageModel> {
        const response = await this.getProjectExecutionsRaw(requestParameters, initOverrides);
        return await response.value();
    }

}

/**
 * @export
 */
export const GetProjectExecutionsJobStatusEnum = {
    Created: 'CREATED',
    Started: 'STARTED',
    Stopped: 'STOPPED',
    Failed: 'FAILED',
    Completed: 'COMPLETED'
} as const;
export type GetProjectExecutionsJobStatusEnum = typeof GetProjectExecutionsJobStatusEnum[keyof typeof GetProjectExecutionsJobStatusEnum];
