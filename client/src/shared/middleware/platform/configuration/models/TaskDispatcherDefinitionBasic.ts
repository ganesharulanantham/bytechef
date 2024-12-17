/* tslint:disable */
/* eslint-disable */
/**
 * The Platform Configuration Internal API
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
import type { Resources } from './Resources';
import {
    ResourcesFromJSON,
    ResourcesFromJSONTyped,
    ResourcesToJSON,
    ResourcesToJSONTyped,
} from './Resources';

/**
 * A task dispatcher defines a strategy for dispatching tasks to be executed.
 * @export
 * @interface TaskDispatcherDefinitionBasic
 */
export interface TaskDispatcherDefinitionBasic {
    /**
     * The description.
     * @type {string}
     * @memberof TaskDispatcherDefinitionBasic
     */
    description?: string;
    /**
     * The icon.
     * @type {string}
     * @memberof TaskDispatcherDefinitionBasic
     */
    icon?: string;
    /**
     * The task dispatcher name..
     * @type {string}
     * @memberof TaskDispatcherDefinitionBasic
     */
    name: string;
    /**
     * 
     * @type {Resources}
     * @memberof TaskDispatcherDefinitionBasic
     */
    resources?: Resources;
    /**
     * The title
     * @type {string}
     * @memberof TaskDispatcherDefinitionBasic
     */
    title?: string;
    /**
     * The version of a task dispatcher.
     * @type {number}
     * @memberof TaskDispatcherDefinitionBasic
     */
    version: number;
}

/**
 * Check if a given object implements the TaskDispatcherDefinitionBasic interface.
 */
export function instanceOfTaskDispatcherDefinitionBasic(value: object): value is TaskDispatcherDefinitionBasic {
    if (!('name' in value) || value['name'] === undefined) return false;
    if (!('version' in value) || value['version'] === undefined) return false;
    return true;
}

export function TaskDispatcherDefinitionBasicFromJSON(json: any): TaskDispatcherDefinitionBasic {
    return TaskDispatcherDefinitionBasicFromJSONTyped(json, false);
}

export function TaskDispatcherDefinitionBasicFromJSONTyped(json: any, ignoreDiscriminator: boolean): TaskDispatcherDefinitionBasic {
    if (json == null) {
        return json;
    }
    return {
        
        'description': json['description'] == null ? undefined : json['description'],
        'icon': json['icon'] == null ? undefined : json['icon'],
        'name': json['name'],
        'resources': json['resources'] == null ? undefined : ResourcesFromJSON(json['resources']),
        'title': json['title'] == null ? undefined : json['title'],
        'version': json['version'],
    };
}

export function TaskDispatcherDefinitionBasicToJSON(json: any): TaskDispatcherDefinitionBasic {
    return TaskDispatcherDefinitionBasicToJSONTyped(json, false);
}

export function TaskDispatcherDefinitionBasicToJSONTyped(value?: TaskDispatcherDefinitionBasic | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'description': value['description'],
        'icon': value['icon'],
        'name': value['name'],
        'resources': ResourcesToJSON(value['resources']),
        'title': value['title'],
        'version': value['version'],
    };
}

