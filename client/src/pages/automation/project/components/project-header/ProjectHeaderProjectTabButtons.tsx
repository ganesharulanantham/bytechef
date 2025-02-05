import {Button} from '@/components/ui/button';
import {Separator} from '@/components/ui/separator';
import {useToast} from '@/hooks/use-toast';
import {useAnalytics} from '@/shared/hooks/useAnalytics';
import {Project} from '@/shared/middleware/automation/configuration';
import {useDuplicateProjectMutation} from '@/shared/mutations/automation/projects.mutations';
import {useCreateProjectWorkflowMutation} from '@/shared/mutations/automation/workflows.mutations';
import {ProjectKeys} from '@/shared/queries/automation/projects.queries';
import {useQueryClient} from '@tanstack/react-query';
import {CopyIcon, DownloadIcon, EditIcon, HistoryIcon, Trash2Icon} from 'lucide-react';
import {ChangeEvent, useRef} from 'react';
import {useNavigate} from 'react-router-dom';

const ProjectHeaderProjectTabButtons = ({
    handleCloseDropdownMenu,
    handleDeleteProject,
    handleEditProject,
    handleShowProjectVersionHistorySheet,
    project,
}: {
    handleCloseDropdownMenu: () => void;
    handleDeleteProject: () => void;
    handleEditProject: () => void;
    handleShowProjectVersionHistorySheet: () => void;
    project: Project;
}) => {
    const hiddenFileInputRef = useRef<HTMLInputElement>(null);

    const {captureProjectWorkflowImported} = useAnalytics();

    const navigate = useNavigate();

    const {toast} = useToast();

    const queryClient = useQueryClient();

    const duplicateProjectMutation = useDuplicateProjectMutation({
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ProjectKeys.projects});

            navigate(`/automation/projects/${project?.id}/project-workflows/${project?.projectWorkflowIds![0]}`);
        },
    });

    const importProjectWorkflowMutation = useCreateProjectWorkflowMutation({
        onSuccess: () => {
            captureProjectWorkflowImported();

            queryClient.invalidateQueries({queryKey: ProjectKeys.project(project.id!)});

            if (hiddenFileInputRef.current) {
                hiddenFileInputRef.current.value = '';
            }

            toast({
                description: 'Workflow is imported.',
            });
        },
    });

    const handleFileChange = async (e: ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            importProjectWorkflowMutation.mutate({
                id: project.id!,
                workflow: {
                    definition: await e.target.files[0].text(),
                },
            });
        }
    };

    const handleButtonClick = (event: React.MouseEvent<HTMLDivElement>) => {
        if ((event.target as HTMLElement).tagName === 'BUTTON') {
            handleCloseDropdownMenu();
        }
    };

    return (
        <div className="flex flex-col" onClick={handleButtonClick}>
            <Button
                className="justify-start rounded-none hover:bg-surface-neutral-primary-hover"
                onClick={() => handleEditProject()}
                variant="ghost"
            >
                <EditIcon /> Edit
            </Button>

            <Button
                className="justify-start rounded-none hover:bg-surface-neutral-primary-hover"
                onClick={() => duplicateProjectMutation.mutate(project.id!)}
                variant="ghost"
            >
                <CopyIcon /> Duplicate
            </Button>

            <Button
                className="justify-start rounded-none hover:bg-surface-neutral-primary-hover"
                onClick={() => {
                    if (hiddenFileInputRef.current) {
                        hiddenFileInputRef.current.click();
                    }
                }}
                variant="ghost"
            >
                <DownloadIcon /> Import Workflow
            </Button>

            <Separator />

            <Button
                className="justify-start rounded-none hover:bg-surface-neutral-primary-hover"
                onClick={() => handleShowProjectVersionHistorySheet()}
                variant="ghost"
            >
                <HistoryIcon /> Project History
            </Button>

            <Separator />

            <Button
                className="justify-start rounded-none text-destructive hover:bg-surface-error-secondary hover:text-destructive"
                onClick={() => handleDeleteProject()}
                variant="ghost"
            >
                <Trash2Icon /> Delete
            </Button>

            <input className="hidden" onChange={handleFileChange} ref={hiddenFileInputRef} type="file" />
        </div>
    );
};

export default ProjectHeaderProjectTabButtons;
