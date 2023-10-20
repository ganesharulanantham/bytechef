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
  ConnectionModel,
  OAuth2AuthorizationParametersModel,
  TagModel,
  UpdateConnectionTagsRequestModel,
} from '../models';
import {
    ConnectionModelFromJSON,
    ConnectionModelToJSON,
    OAuth2AuthorizationParametersModelFromJSON,
    OAuth2AuthorizationParametersModelToJSON,
    TagModelFromJSON,
    TagModelToJSON,
    UpdateConnectionTagsRequestModelFromJSON,
    UpdateConnectionTagsRequestModelToJSON,
} from '../models';

export interface CreateConnectionRequest {
    connectionModel: ConnectionModel;
}

export interface DeleteConnectionRequest {
    id: number;
}

export interface GetConnectionRequest {
    id: number;
}

export interface GetConnectionOAuth2AuthorizationParametersRequest {
    connectionModel: ConnectionModel;
}

export interface GetConnectionsRequest {
    componentNames?: Array<string>;
    tagIds?: Array<number>;
}

export interface UpdateConnectionRequest {
    id: number;
    connectionModel: ConnectionModel;
}

export interface UpdateConnectionTagsRequest {
    id: number;
    updateConnectionTagsRequestModel: UpdateConnectionTagsRequestModel;
}

/**
 * 
 */
export class ConnectionsApi extends runtime.BaseAPI {

    /**
     * Create a new connection.
     * Create a new connection.
     */
    async createConnectionRaw(requestParameters: CreateConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ConnectionModel>> {
        if (requestParameters.connectionModel === null || requestParameters.connectionModel === undefined) {
            throw new runtime.RequiredError('connectionModel','Required parameter requestParameters.connectionModel was null or undefined when calling createConnection.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/connections`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ConnectionModelToJSON(requestParameters.connectionModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ConnectionModelFromJSON(jsonValue));
    }

    /**
     * Create a new connection.
     * Create a new connection.
     */
    async createConnection(requestParameters: CreateConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ConnectionModel> {
        const response = await this.createConnectionRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete a connection.
     * Delete a connection.
     */
    async deleteConnectionRaw(requestParameters: DeleteConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling deleteConnection.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/connections/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Delete a connection.
     * Delete a connection.
     */
    async deleteConnection(requestParameters: DeleteConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.deleteConnectionRaw(requestParameters, initOverrides);
    }

    /**
     * Get a connection by id.
     * Get a connection by id.
     */
    async getConnectionRaw(requestParameters: GetConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ConnectionModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling getConnection.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/connections/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ConnectionModelFromJSON(jsonValue));
    }

    /**
     * Get a connection by id.
     * Get a connection by id.
     */
    async getConnection(requestParameters: GetConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ConnectionModel> {
        const response = await this.getConnectionRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Populates a new connection with oauth parameters.
     * Populates a new connection with oauth parameters.
     */
    async getConnectionOAuth2AuthorizationParametersRaw(requestParameters: GetConnectionOAuth2AuthorizationParametersRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<OAuth2AuthorizationParametersModel>> {
        if (requestParameters.connectionModel === null || requestParameters.connectionModel === undefined) {
            throw new runtime.RequiredError('connectionModel','Required parameter requestParameters.connectionModel was null or undefined when calling getConnectionOAuth2AuthorizationParameters.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/connections/oauth2`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ConnectionModelToJSON(requestParameters.connectionModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => OAuth2AuthorizationParametersModelFromJSON(jsonValue));
    }

    /**
     * Populates a new connection with oauth parameters.
     * Populates a new connection with oauth parameters.
     */
    async getConnectionOAuth2AuthorizationParameters(requestParameters: GetConnectionOAuth2AuthorizationParametersRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<OAuth2AuthorizationParametersModel> {
        const response = await this.getConnectionOAuth2AuthorizationParametersRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get connection tags.
     * Get connection tags.
     */
    async getConnectionTagsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<TagModel>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/connections/tags`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(TagModelFromJSON));
    }

    /**
     * Get connection tags.
     * Get connection tags.
     */
    async getConnectionTags(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<TagModel>> {
        const response = await this.getConnectionTagsRaw(initOverrides);
        return await response.value();
    }

    /**
     * Get connections.
     * Get connections.
     */
    async getConnectionsRaw(requestParameters: GetConnectionsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<ConnectionModel>>> {
        const queryParameters: any = {};

        if (requestParameters.componentNames) {
            queryParameters['componentNames'] = requestParameters.componentNames;
        }

        if (requestParameters.tagIds) {
            queryParameters['tagIds'] = requestParameters.tagIds;
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/connections`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(ConnectionModelFromJSON));
    }

    /**
     * Get connections.
     * Get connections.
     */
    async getConnections(requestParameters: GetConnectionsRequest = {}, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<ConnectionModel>> {
        const response = await this.getConnectionsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Update an existing connection.
     * Update an existing connection.
     */
    async updateConnectionRaw(requestParameters: UpdateConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ConnectionModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateConnection.');
        }

        if (requestParameters.connectionModel === null || requestParameters.connectionModel === undefined) {
            throw new runtime.RequiredError('connectionModel','Required parameter requestParameters.connectionModel was null or undefined when calling updateConnection.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/connections/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: ConnectionModelToJSON(requestParameters.connectionModel),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ConnectionModelFromJSON(jsonValue));
    }

    /**
     * Update an existing connection.
     * Update an existing connection.
     */
    async updateConnection(requestParameters: UpdateConnectionRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ConnectionModel> {
        const response = await this.updateConnectionRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Updates tags of an existing connection.
     * Updates tags of an existing connection.
     */
    async updateConnectionTagsRaw(requestParameters: UpdateConnectionTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateConnectionTags.');
        }

        if (requestParameters.updateConnectionTagsRequestModel === null || requestParameters.updateConnectionTagsRequestModel === undefined) {
            throw new runtime.RequiredError('updateConnectionTagsRequestModel','Required parameter requestParameters.updateConnectionTagsRequestModel was null or undefined when calling updateConnectionTags.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/connections/{id}/tags`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: UpdateConnectionTagsRequestModelToJSON(requestParameters.updateConnectionTagsRequestModel),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Updates tags of an existing connection.
     * Updates tags of an existing connection.
     */
    async updateConnectionTags(requestParameters: UpdateConnectionTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateConnectionTagsRaw(requestParameters, initOverrides);
    }

}
