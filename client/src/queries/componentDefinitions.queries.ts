import {
    ComponentDefinitionApi,
    ComponentDefinitionBasicModel,
    ComponentDefinitionModel,
    GetComponentDefinitionRequest,
    GetComponentDefinitionsRequest,
} from '@/middleware/hermes/configuration';
import {useQuery} from '@tanstack/react-query';

export const ComponentDefinitionKeys = {
    componentDefinition: (request: GetComponentDefinitionRequest) => [
        ComponentDefinitionKeys.componentDefinitions,
        request,
    ],
    componentDefinitions: ['componentDefinitions'] as const,
    filteredComponentDefinitions: (
        request?: GetComponentDefinitionsRequest
    ) => [ComponentDefinitionKeys.componentDefinitions, request],
};

export const useGetComponentDefinitionQuery = (
    request: GetComponentDefinitionRequest,
    enabledCondition?: boolean
) =>
    useQuery<ComponentDefinitionModel, Error>(
        ComponentDefinitionKeys.componentDefinition(request),
        () => new ComponentDefinitionApi().getComponentDefinition(request),
        {
            enabled: false || enabledCondition,
        }
    );

export const useGetComponentDefinitionsQuery = (
    request?: GetComponentDefinitionsRequest,
    enabledCondition?: boolean
) =>
    useQuery<ComponentDefinitionBasicModel[], Error>(
        ComponentDefinitionKeys.filteredComponentDefinitions(request),
        () => new ComponentDefinitionApi().getComponentDefinitions(request),
        {
            enabled: false || enabledCondition,
        }
    );
