/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.bytechef.automation.workflow.execution.web.rest;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.bytechef.automation.workflow.execution.web.rest.model.WorkflowExecutionModel;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T06:46:19.862170+02:00[Europe/Zagreb]", comments = "Generator version: 7.4.0")
@Validated
@Tag(name = "workflow-execution", description = "The Automation Workflow Execution API")
public interface WorkflowExecutionApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /workflow-executions/{id} : Get workflow executions by id
     * Get workflow executions by id.
     *
     * @param id The id of an execution. (required)
     * @return The execution object. (status code 200)
     */
    @Operation(
        operationId = "getWorkflowExecution",
        summary = "Get workflow executions by id",
        description = "Get workflow executions by id.",
        tags = { "workflow-execution" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The execution object.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowExecutionModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workflow-executions/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<WorkflowExecutionModel> getWorkflowExecution(
        @Parameter(name = "id", description = "The id of an execution.", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"triggerExecution\" : { \"retryDelayFactor\" : 6, \"endDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"batch\" : true, \"error\" : { \"stackTrace\" : [ \"stackTrace\", \"stackTrace\" ], \"message\" : \"message\" }, \"priority\" : 1, \"type\" : \"type\", \"executionTime\" : 3, \"output\" : \"{}\", \"retryDelay\" : \"retryDelay\", \"input\" : { \"key\" : \"\" }, \"retryDelayMillis\" : 6, \"component\" : { \"icon\" : \"icon\", \"name\" : \"name\", \"actionsCount\" : 5, \"description\" : \"description\", \"title\" : \"title\", \"version\" : 7, \"triggersCount\" : 2 }, \"maxRetries\" : 6, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"workflowTrigger\" : { \"name\" : \"name\", \"description\" : \"description\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"\" }, \"connections\" : [ { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true }, { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true } ], \"timeout\" : \"timeout\" }, \"id\" : \"id\", \"retryAttempts\" : 2, \"startDate\" : \"2000-01-23T04:56:07.000+00:00\", \"status\" : \"CREATED\" }, \"workflow\" : { \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"workflowTaskComponentNames\" : [ \"workflowTaskComponentNames\", \"workflowTaskComponentNames\" ], \"projectWorkflowId\" : 3.0937452626664474, \"label\" : \"label\", \"inputsCount\" : 6, \"connectionsCount\" : 5, \"__version\" : 3, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"manualTrigger\" : true, \"workflowTriggerComponentNames\" : [ \"workflowTriggerComponentNames\", \"workflowTriggerComponentNames\" ], \"id\" : \"id\" }, \"project\" : { \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 9, \"publishedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"projectVersion\" : 6 }, \"id\" : 0, \"projectInstance\" : { \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 8, \"lastExecutionDate\" : \"2000-01-23T04:56:07.000+00:00\", \"projectId\" : 9, \"enabled\" : true, \"projectVersion\" : 6 }, \"job\" : { \"outputs\" : { \"key\" : \"\" }, \"taskExecutions\" : [ { \"endDate\" : \"2000-01-23T04:56:07.000+00:00\", \"error\" : { \"stackTrace\" : [ \"stackTrace\", \"stackTrace\" ], \"message\" : \"message\" }, \"type\" : \"type\", \"output\" : \"{}\", \"retryDelay\" : \"retryDelay\", \"retryDelayMillis\" : 1, \"id\" : \"id\", \"retryAttempts\" : 7, \"retryDelayFactor\" : 1, \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"workflowTask\" : { \"pre\" : [ null, null ], \"destination\" : { \"componentName\" : \"componentName\", \"componentVersion\" : 7 }, \"description\" : \"description\", \"label\" : \"label\", \"source\" : { \"componentName\" : \"componentName\", \"componentVersion\" : 7 }, \"type\" : \"type\", \"timeout\" : \"timeout\", \"node\" : \"node\", \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"parameters\" : { \"key\" : \"\" }, \"connections\" : [ { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true }, { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true } ] }, \"priority\" : 2, \"parentId\" : \"parentId\", \"executionTime\" : 9, \"input\" : { \"key\" : \"\" }, \"jobId\" : \"jobId\", \"component\" : { \"icon\" : \"icon\", \"name\" : \"name\", \"actionsCount\" : 5, \"description\" : \"description\", \"title\" : \"title\", \"version\" : 7, \"triggersCount\" : 2 }, \"maxRetries\" : 3, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"progress\" : 4, \"taskNumber\" : 1, \"startDate\" : \"2000-01-23T04:56:07.000+00:00\", \"status\" : \"CREATED\" }, { \"endDate\" : \"2000-01-23T04:56:07.000+00:00\", \"error\" : { \"stackTrace\" : [ \"stackTrace\", \"stackTrace\" ], \"message\" : \"message\" }, \"type\" : \"type\", \"output\" : \"{}\", \"retryDelay\" : \"retryDelay\", \"retryDelayMillis\" : 1, \"id\" : \"id\", \"retryAttempts\" : 7, \"retryDelayFactor\" : 1, \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedBy\" : \"lastModifiedBy\", \"workflowTask\" : { \"pre\" : [ null, null ], \"destination\" : { \"componentName\" : \"componentName\", \"componentVersion\" : 7 }, \"description\" : \"description\", \"label\" : \"label\", \"source\" : { \"componentName\" : \"componentName\", \"componentVersion\" : 7 }, \"type\" : \"type\", \"timeout\" : \"timeout\", \"node\" : \"node\", \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"parameters\" : { \"key\" : \"\" }, \"connections\" : [ { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true }, { \"workflowNodeName\" : \"workflowNodeName\", \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\", \"required\" : true } ] }, \"priority\" : 2, \"parentId\" : \"parentId\", \"executionTime\" : 9, \"input\" : { \"key\" : \"\" }, \"jobId\" : \"jobId\", \"component\" : { \"icon\" : \"icon\", \"name\" : \"name\", \"actionsCount\" : 5, \"description\" : \"description\", \"title\" : \"title\", \"version\" : 7, \"triggersCount\" : 2 }, \"maxRetries\" : 3, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"progress\" : 4, \"taskNumber\" : 1, \"startDate\" : \"2000-01-23T04:56:07.000+00:00\", \"status\" : \"CREATED\" } ], \"endDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : { \"key\" : \"\" }, \"lastModifiedBy\" : \"lastModifiedBy\", \"currentTask\" : 6, \"label\" : \"label\", \"error\" : { \"stackTrace\" : [ \"stackTrace\", \"stackTrace\" ], \"message\" : \"message\" }, \"priority\" : 5, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"parentTaskExecutionId\" : 1, \"webhooks\" : [ { \"type\" : \"type\", \"url\" : \"url\", \"retry\" : { \"maxAttempts\" : 5, \"multiplier\" : 9, \"initialInterval\" : 1, \"maxInterval\" : 4 } }, { \"type\" : \"type\", \"url\" : \"url\", \"retry\" : { \"maxAttempts\" : 5, \"multiplier\" : 9, \"initialInterval\" : 1, \"maxInterval\" : 4 } } ], \"id\" : \"id\", \"startDate\" : \"2000-01-23T04:56:07.000+00:00\", \"workflowId\" : \"workflowId\", \"status\" : \"CREATED\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /workflow-executions : Get project workflow executions
     * Get project workflow executions.
     *
     * @param jobStatus The status of an executed job (optional)
     * @param jobStartDate The start date of a job. (optional)
     * @param jobEndDate The end date of a job. (optional)
     * @param projectId The id of a project. (optional)
     * @param projectInstanceId The id of a project instance. (optional)
     * @param workflowId The id of a workflow. (optional)
     * @param pageNumber The number of the page to return. (optional, default to 0)
     * @return The page of workflow executions. (status code 200)
     */
    @Operation(
        operationId = "getWorkflowExecutionsPage",
        summary = "Get project workflow executions",
        description = "Get project workflow executions.",
        tags = { "workflow-execution" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The page of workflow executions.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = org.springframework.data.domain.Page.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workflow-executions",
        produces = { "application/json" }
    )
    
    default ResponseEntity<org.springframework.data.domain.Page> getWorkflowExecutionsPage(
        @Parameter(name = "jobStatus", description = "The status of an executed job", in = ParameterIn.QUERY) @Valid @RequestParam(value = "jobStatus", required = false) String jobStatus,
        @Parameter(name = "jobStartDate", description = "The start date of a job.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "jobStartDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime jobStartDate,
        @Parameter(name = "jobEndDate", description = "The end date of a job.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "jobEndDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime jobEndDate,
        @Parameter(name = "projectId", description = "The id of a project.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "projectId", required = false) Long projectId,
        @Parameter(name = "projectInstanceId", description = "The id of a project instance.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "projectInstanceId", required = false) Long projectInstanceId,
        @Parameter(name = "workflowId", description = "The id of a workflow.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "workflowId", required = false) String workflowId,
        @Parameter(name = "pageNumber", description = "The number of the page to return.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 0, \"size\" : 6, \"numberOfElements\" : 1, \"totalPages\" : 5, \"content\" : [ \"{}\", \"{}\" ], \"totalElements\" : 5 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
