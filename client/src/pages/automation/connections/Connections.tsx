import EmptyList from '@/components/EmptyList';
import PageLoader from '@/components/PageLoader';
import {Button} from '@/components/ui/button';
import {useWorkspaceStore} from '@/pages/automation/stores/useWorkspaceStore';
import ConnectionDialog from '@/pages/platform/connection/components/ConnectionDialog';
import LayoutContainer from '@/shared/layout/LayoutContainer';
import {LeftSidebarNav, LeftSidebarNavItem} from '@/shared/layout/LeftSidebarNav';
import PageHeader from '@/shared/layout/PageHeader';
import {useCreateConnectionMutation} from '@/shared/mutations/automation/connections.mutations';
import {
    ConnectionKeys,
    useGetConnectionTagsQuery,
    useGetWorkspaceConnectionsQuery,
} from '@/shared/queries/automation/connections.queries';
import {useGetComponentDefinitionsQuery} from '@/shared/queries/platform/componentDefinitions.queries';
import {Link2Icon, TagIcon} from 'lucide-react';
import {useState} from 'react';
import {useSearchParams} from 'react-router-dom';

import ConnectionList from './components/ConnectionList';

export enum Type {
    Component,
    Tag,
}

export const Connections = () => {
    const {currentWorkspaceId} = useWorkspaceStore();

    const [searchParams] = useSearchParams();

    const defaultCurrentState = {
        id: searchParams.get('componentName')
            ? searchParams.get('componentName')!
            : searchParams.get('tagId')
              ? parseInt(searchParams.get('tagId')!)
              : undefined,
        type: searchParams.get('tagId') ? Type.Tag : Type.Component,
    };

    const [filterData, setFilterData] = useState<{
        id?: number | string;
        type: Type;
    }>(defaultCurrentState);

    const {
        data: allConnections,
        error: allConnectionsError,
        isLoading: allConnectionsIsLoading,
    } = useGetWorkspaceConnectionsQuery({id: currentWorkspaceId!});

    const allComponentNames = allConnections?.map((connection) => connection.componentName);

    const {data: componentDefinitions, isLoading: componentsLoading} = useGetComponentDefinitionsQuery(
        {include: allComponentNames},
        allComponentNames !== undefined
    );

    const {
        data: connections,
        error: connectionsError,
        isLoading: connectionsIsLoading,
    } = useGetWorkspaceConnectionsQuery({
        componentName: searchParams.get('componentName') ? searchParams.get('componentName')! : undefined,
        id: currentWorkspaceId!,
        tagId: searchParams.get('tagId') ? parseInt(searchParams.get('tagId')!) : undefined,
    });

    const {data: tags, error: tagsError, isLoading: tagsIsLoading} = useGetConnectionTagsQuery();

    let pageTitle: string | undefined;

    if (filterData.type === Type.Component) {
        pageTitle = componentDefinitions?.find(
            (componentDefinition) => componentDefinition.name === filterData.id
        )?.title;
    } else {
        pageTitle = tags?.find((tag) => tag.id === filterData.id)?.name;
    }

    return (
        <LayoutContainer
            header={
                connections &&
                connections.length > 0 && (
                    <PageHeader
                        centerTitle={true}
                        position="main"
                        right={
                            <ConnectionDialog
                                connectionTagsQueryKey={ConnectionKeys.connectionTags}
                                connectionsQueryKey={ConnectionKeys.connections}
                                triggerNode={<Button>New Connection</Button>}
                                useCreateConnectionMutation={useCreateConnectionMutation}
                                useGetConnectionTagsQuery={useGetConnectionTagsQuery}
                            />
                        }
                        title={
                            !pageTitle
                                ? 'All Connections'
                                : `Filter by ${searchParams.get('tagId') ? 'tag' : 'component'}: ${pageTitle}`
                        }
                    />
                )
            }
            leftSidebarBody={
                <>
                    <LeftSidebarNav
                        body={
                            <>
                                <LeftSidebarNavItem
                                    item={{
                                        filterData: !filterData?.id && filterData.type === Type.Component,
                                        name: 'All Components',
                                        onItemClick: (id?: number | string) => {
                                            setFilterData({
                                                id,
                                                type: Type.Component,
                                            });
                                        },
                                    }}
                                />

                                {!componentsLoading &&
                                    componentDefinitions?.map((item) => (
                                        <LeftSidebarNavItem
                                            item={{
                                                filterData:
                                                    filterData?.id === item.name && filterData.type === Type.Component,
                                                id: item.name!,
                                                name: item.title!,
                                                onItemClick: (id?: number | string) =>
                                                    setFilterData({
                                                        id,
                                                        type: Type.Component,
                                                    }),
                                            }}
                                            key={item.name}
                                            toLink={`?componentName=${item.name}`}
                                        />
                                    ))}
                            </>
                        }
                        title="Components"
                    />

                    <LeftSidebarNav
                        body={
                            <>
                                {!tagsIsLoading &&
                                    (!tags?.length ? (
                                        <p className="px-3 text-xs">No tags.</p>
                                    ) : (
                                        tags?.map((item) => (
                                            <LeftSidebarNavItem
                                                icon={<TagIcon className="mr-1 size-4" />}
                                                item={{
                                                    filterData:
                                                        filterData?.id === item.id && filterData.type === Type.Tag,
                                                    id: item.id!,
                                                    name: item.name,
                                                    onItemClick: (id?: number | string) => {
                                                        setFilterData({
                                                            id,
                                                            type: Type.Tag,
                                                        });
                                                    },
                                                }}
                                                key={item.id}
                                                toLink={`?tagId=${item.id}`}
                                            />
                                        ))
                                    ))}
                            </>
                        }
                        title="Tags"
                    />
                </>
            }
            leftSidebarHeader={<PageHeader position="sidebar" title="Connections" />}
        >
            <PageLoader
                errors={[allConnectionsError, connectionsError, tagsError]}
                loading={allConnectionsIsLoading || connectionsIsLoading || tagsIsLoading}
            >
                {connections && connections?.length > 0 ? (
                    connections && tags && <ConnectionList connections={connections} tags={tags} />
                ) : (
                    <EmptyList
                        button={
                            <ConnectionDialog
                                connectionTagsQueryKey={ConnectionKeys.connectionTags}
                                connectionsQueryKey={ConnectionKeys.connections}
                                triggerNode={<Button>Create Connection</Button>}
                                useCreateConnectionMutation={useCreateConnectionMutation}
                                useGetConnectionTagsQuery={useGetConnectionTagsQuery}
                            />
                        }
                        icon={<Link2Icon className="size-12 text-gray-400" />}
                        message="You do not have any Connections created yet."
                        title="No Connections"
                    />
                )}
            </PageLoader>
        </LayoutContainer>
    );
};
