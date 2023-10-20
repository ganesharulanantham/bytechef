/* tslint:disable */
/* eslint-disable */
/**
 * Definition API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import type { ControlTypeModel } from './ControlTypeModel';
import {
    ControlTypeModelFromJSON,
    ControlTypeModelFromJSONTyped,
    ControlTypeModelToJSON,
} from './ControlTypeModel';
import type { OptionModel } from './OptionModel';
import {
    OptionModelFromJSON,
    OptionModelFromJSONTyped,
    OptionModelToJSON,
} from './OptionModel';
import type { OptionsDataSourceModel } from './OptionsDataSourceModel';
import {
    OptionsDataSourceModelFromJSON,
    OptionsDataSourceModelFromJSONTyped,
    OptionsDataSourceModelToJSON,
} from './OptionsDataSourceModel';
import type { PropertyTypeModel } from './PropertyTypeModel';
import {
    PropertyTypeModelFromJSON,
    PropertyTypeModelFromJSONTyped,
    PropertyTypeModelToJSON,
} from './PropertyTypeModel';
import type { ValuePropertyModel } from './ValuePropertyModel';
import {
    ValuePropertyModelFromJSON,
    ValuePropertyModelFromJSONTyped,
    ValuePropertyModelToJSON,
} from './ValuePropertyModel';

/**
 * A number property type.
 * @export
 * @interface NumberPropertyModel
 */
export interface NumberPropertyModel extends ValuePropertyModel {
    /**
     * The maximum property value.
     * @type {number}
     * @memberof NumberPropertyModel
     */
    maxValue?: number;
    /**
     * The minimum property value.
     * @type {number}
     * @memberof NumberPropertyModel
     */
    minValue?: number;
    /**
     * The number value precision.
     * @type {number}
     * @memberof NumberPropertyModel
     */
    numberPrecision?: number;
    /**
     * The list of valid property options.
     * @type {Array<OptionModel>}
     * @memberof NumberPropertyModel
     */
    options?: Array<OptionModel>;
    /**
     * 
     * @type {OptionsDataSourceModel}
     * @memberof NumberPropertyModel
     */
    optionsDataSource?: OptionsDataSourceModel;
}

/**
 * Check if a given object implements the NumberPropertyModel interface.
 */
export function instanceOfNumberPropertyModel(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function NumberPropertyModelFromJSON(json: any): NumberPropertyModel {
    return NumberPropertyModelFromJSONTyped(json, false);
}

export function NumberPropertyModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): NumberPropertyModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        ...ValuePropertyModelFromJSONTyped(json, ignoreDiscriminator),
        'maxValue': !exists(json, 'maxValue') ? undefined : json['maxValue'],
        'minValue': !exists(json, 'minValue') ? undefined : json['minValue'],
        'numberPrecision': !exists(json, 'numberPrecision') ? undefined : json['numberPrecision'],
        'options': !exists(json, 'options') ? undefined : ((json['options'] as Array<any>).map(OptionModelFromJSON)),
        'optionsDataSource': !exists(json, 'optionsDataSource') ? undefined : OptionsDataSourceModelFromJSON(json['optionsDataSource']),
    };
}

export function NumberPropertyModelToJSON(value?: NumberPropertyModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        ...ValuePropertyModelToJSON(value),
        'maxValue': value.maxValue,
        'minValue': value.minValue,
        'numberPrecision': value.numberPrecision,
        'options': value.options === undefined ? undefined : ((value.options as Array<any>).map(OptionModelToJSON)),
        'optionsDataSource': OptionsDataSourceModelToJSON(value.optionsDataSource),
    };
}

