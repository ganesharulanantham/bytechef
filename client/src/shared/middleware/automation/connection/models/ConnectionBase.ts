/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Connection Internal API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
import type { CredentialStatus } from './CredentialStatus';
import {
    CredentialStatusFromJSON,
    CredentialStatusFromJSONTyped,
    CredentialStatusToJSON,
    CredentialStatusToJSONTyped,
} from './CredentialStatus';
import type { Tag } from './Tag';
import {
    TagFromJSON,
    TagFromJSONTyped,
    TagToJSON,
    TagToJSONTyped,
} from './Tag';
import type { ConnectionEnvironment } from './ConnectionEnvironment';
import {
    ConnectionEnvironmentFromJSON,
    ConnectionEnvironmentFromJSONTyped,
    ConnectionEnvironmentToJSON,
    ConnectionEnvironmentToJSONTyped,
} from './ConnectionEnvironment';

/**
 * Contains all required information to open a connection to a service defined by componentName parameter.
 * @export
 * @interface ConnectionBase
 */
export interface ConnectionBase {
    /**
     * If a connection is used in any of active workflows.
     * @type {boolean}
     * @memberof ConnectionBase
     */
    readonly active?: boolean;
    /**
     * The name of an authorization used by this connection. Used for HTTP based services.
     * @type {string}
     * @memberof ConnectionBase
     */
    authorizationName?: string;
    /**
     * The authorization parameters of a connection.
     * @type {{ [key: string]: any; }}
     * @memberof ConnectionBase
     */
    readonly authorizationParameters?: { [key: string]: any; };
    /**
     * The name of a component that uses this connection.
     * @type {string}
     * @memberof ConnectionBase
     */
    componentName: string;
    /**
     * The connection parameters of a connection.
     * @type {{ [key: string]: any; }}
     * @memberof ConnectionBase
     */
    readonly connectionParameters?: { [key: string]: any; };
    /**
     * The version of a component that uses this connection.
     * @type {number}
     * @memberof ConnectionBase
     */
    connectionVersion: number;
    /**
     * The created by.
     * @type {string}
     * @memberof ConnectionBase
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof ConnectionBase
     */
    readonly createdDate?: Date;
    /**
     * 
     * @type {CredentialStatus}
     * @memberof ConnectionBase
     */
    credentialStatus?: CredentialStatus;
    /**
     * 
     * @type {ConnectionEnvironment}
     * @memberof ConnectionBase
     */
    environment?: ConnectionEnvironment;
    /**
     * The id of a connection.
     * @type {number}
     * @memberof ConnectionBase
     */
    readonly id?: number;
    /**
     * The last modified by.
     * @type {string}
     * @memberof ConnectionBase
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof ConnectionBase
     */
    readonly lastModifiedDate?: Date;
    /**
     * The name of a connection.
     * @type {string}
     * @memberof ConnectionBase
     */
    name: string;
    /**
     * The parameters of a connection.
     * @type {{ [key: string]: any; }}
     * @memberof ConnectionBase
     */
    parameters: { [key: string]: any; };
    /**
     * 
     * @type {Array<Tag>}
     * @memberof ConnectionBase
     */
    tags?: Array<Tag>;
    /**
     * 
     * @type {number}
     * @memberof ConnectionBase
     */
    version?: number;
}



/**
 * Check if a given object implements the ConnectionBase interface.
 */
export function instanceOfConnectionBase(value: object): value is ConnectionBase {
    if (!('componentName' in value) || value['componentName'] === undefined) return false;
    if (!('connectionVersion' in value) || value['connectionVersion'] === undefined) return false;
    if (!('name' in value) || value['name'] === undefined) return false;
    if (!('parameters' in value) || value['parameters'] === undefined) return false;
    return true;
}

export function ConnectionBaseFromJSON(json: any): ConnectionBase {
    return ConnectionBaseFromJSONTyped(json, false);
}

export function ConnectionBaseFromJSONTyped(json: any, ignoreDiscriminator: boolean): ConnectionBase {
    if (json == null) {
        return json;
    }
    return {
        
        'active': json['active'] == null ? undefined : json['active'],
        'authorizationName': json['authorizationName'] == null ? undefined : json['authorizationName'],
        'authorizationParameters': json['authorizationParameters'] == null ? undefined : json['authorizationParameters'],
        'componentName': json['componentName'],
        'connectionParameters': json['connectionParameters'] == null ? undefined : json['connectionParameters'],
        'connectionVersion': json['connectionVersion'],
        'createdBy': json['createdBy'] == null ? undefined : json['createdBy'],
        'createdDate': json['createdDate'] == null ? undefined : (new Date(json['createdDate'])),
        'credentialStatus': json['credentialStatus'] == null ? undefined : CredentialStatusFromJSON(json['credentialStatus']),
        'environment': json['environment'] == null ? undefined : ConnectionEnvironmentFromJSON(json['environment']),
        'id': json['id'] == null ? undefined : json['id'],
        'lastModifiedBy': json['lastModifiedBy'] == null ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': json['lastModifiedDate'] == null ? undefined : (new Date(json['lastModifiedDate'])),
        'name': json['name'],
        'parameters': json['parameters'],
        'tags': json['tags'] == null ? undefined : ((json['tags'] as Array<any>).map(TagFromJSON)),
        'version': json['__version'] == null ? undefined : json['__version'],
    };
}

export function ConnectionBaseToJSON(json: any): ConnectionBase {
    return ConnectionBaseToJSONTyped(json, false);
}

export function ConnectionBaseToJSONTyped(value?: Omit<ConnectionBase, 'active'|'authorizationParameters'|'connectionParameters'|'createdBy'|'createdDate'|'id'|'lastModifiedBy'|'lastModifiedDate'> | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'authorizationName': value['authorizationName'],
        'componentName': value['componentName'],
        'connectionVersion': value['connectionVersion'],
        'credentialStatus': CredentialStatusToJSON(value['credentialStatus']),
        'environment': ConnectionEnvironmentToJSON(value['environment']),
        'name': value['name'],
        'parameters': value['parameters'],
        'tags': value['tags'] == null ? undefined : ((value['tags'] as Array<any>).map(TagToJSON)),
        '__version': value['version'],
    };
}

