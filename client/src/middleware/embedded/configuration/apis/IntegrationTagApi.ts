/* tslint:disable */
/* eslint-disable */
/**
 * The Embedded Configuration API
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
  TagModel,
  UpdateTagsRequestModel,
} from '../models/index';
import {
    TagModelFromJSON,
    TagModelToJSON,
    UpdateTagsRequestModelFromJSON,
    UpdateTagsRequestModelToJSON,
} from '../models/index';

export interface UpdateIntegrationTagsRequest {
    id: number;
    updateTagsRequestModel: UpdateTagsRequestModel;
}

/**
 * 
 */
export class IntegrationTagApi extends runtime.BaseAPI {

    /**
     * Get integration tags.
     * Get integration tags
     */
    async getIntegrationTagsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<TagModel>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/integrations/tags`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(TagModelFromJSON));
    }

    /**
     * Get integration tags.
     * Get integration tags
     */
    async getIntegrationTags(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<TagModel>> {
        const response = await this.getIntegrationTagsRaw(initOverrides);
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
