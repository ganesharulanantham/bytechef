/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.bytechef.helios.configuration.web.rest;

import com.bytechef.helios.configuration.web.rest.model.WorkflowModel;
import com.bytechef.helios.configuration.web.rest.model.WorkflowRequestModel;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-01T06:10:09.658506+01:00[Europe/Zagreb]")
@Validated
@Tag(name = "workflow", description = "The Automation Workflow API")
public interface WorkflowApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /projects/{id}/workflows/{workflowId} : Delete a workflow
     * Delete a workflow.
     *
     * @param id The id of a project. (required)
     * @param workflowId The id of the workflow to delete. (required)
     * @return Successful operation. (status code 200)
     */
    @Operation(
        operationId = "deleteProjectWorkflow",
        summary = "Delete a workflow",
        description = "Delete a workflow.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation.")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/projects/{id}/workflows/{workflowId}"
    )
    default ResponseEntity<Void> deleteProjectWorkflow(
        @Parameter(name = "id", description = "The id of a project.", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "workflowId", description = "The id of the workflow to delete.", required = true, in = ParameterIn.PATH) @PathVariable("workflowId") String workflowId
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /projects/{id}/workflows/{workflowId}/duplicate : Duplicates existing workflow.
     * Duplicates existing workflow.
     *
     * @param id The id of a project. (required)
     * @param workflowId The id of a workflow. (required)
     * @return The duplicated workflow object. (status code 200)
     */
    @Operation(
        operationId = "duplicateWorkflow",
        summary = "Duplicates existing workflow.",
        description = "Duplicates existing workflow.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The duplicated workflow object.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/projects/{id}/workflows/{workflowId}/duplicate",
        produces = { "application/json" }
    )
    default ResponseEntity<WorkflowModel> duplicateWorkflow(
        @Parameter(name = "id", description = "The id of a project.", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "workflowId", description = "The id of a workflow.", required = true, in = ParameterIn.PATH) @PathVariable("workflowId") String workflowId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /projects/{id}/workflows : Get workflows for particular project.
     * Get workflows for particular project.
     *
     * @param id The id of a project. (required)
     * @return The updated project object. (status code 200)
     */
    @Operation(
        operationId = "getProjectWorkflows",
        summary = "Get workflows for particular project.",
        description = "Get workflows for particular project.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The updated project object.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WorkflowModel.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/projects/{id}/workflows",
        produces = { "application/json" }
    )
    default ResponseEntity<List<WorkflowModel>> getProjectWorkflows(
        @Parameter(name = "id", description = "The id of a project.", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] }, { \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /workflows/{id} : Get a workflow by id
     * Get a workflow by id.
     *
     * @param id The id of the workflow to get. (required)
     * @return The workflow object. (status code 200)
     */
    @Operation(
        operationId = "getWorkflow",
        summary = "Get a workflow by id",
        description = "Get a workflow by id.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The workflow object.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workflows/{id}",
        produces = { "application/json" }
    )
    default ResponseEntity<WorkflowModel> getWorkflow(
        @Parameter(name = "id", description = "The id of the workflow to get.", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /workflows : Get workflow definitions
     * Get workflow definitions.
     *
     * @return A list of workflows. (status code 200)
     */
    @Operation(
        operationId = "getWorkflows",
        summary = "Get workflow definitions",
        description = "Get workflow definitions.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "A list of workflows.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WorkflowModel.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workflows",
        produces = { "application/json" }
    )
    default ResponseEntity<List<WorkflowModel>> getWorkflows(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] }, { \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /workflows/{id} : Update an existing workflow
     * Update an existing workflow.
     *
     * @param id The id of the workflow to update. (required)
     * @param workflowRequestModel  (required)
     * @return The updated workflow object. (status code 200)
     */
    @Operation(
        operationId = "updateWorkflow",
        summary = "Update an existing workflow",
        description = "Update an existing workflow.",
        tags = { "workflow" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The updated workflow object.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/workflows/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<WorkflowModel> updateWorkflow(
        @Parameter(name = "id", description = "The id of the workflow to update.", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
        @Parameter(name = "WorkflowRequestModel", description = "", required = true) @Valid @RequestBody WorkflowRequestModel workflowRequestModel
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"outputs\" : [ { \"name\" : \"name\", \"value\" : \"{}\" }, { \"name\" : \"name\", \"value\" : \"{}\" } ], \"lastModifiedDate\" : \"2000-01-23T04:56:07.000+00:00\", \"inputs\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"required\" : false } ], \"lastModifiedBy\" : \"lastModifiedBy\", \"description\" : \"description\", \"label\" : \"label\", \"triggers\" : [ { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"name\" : \"name\", \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ], \"__version\" : 1, \"maxRetries\" : 0, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"createdBy\" : \"createdBy\", \"sourceType\" : \"CLASSPATH\", \"definition\" : \"definition\", \"id\" : \"id\", \"tasks\" : [ { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" }, { \"node\" : \"node\", \"pre\" : [ null, null ], \"post\" : [ null, null ], \"name\" : \"name\", \"finalize\" : [ null, null ], \"label\" : \"label\", \"type\" : \"type\", \"parameters\" : { \"key\" : \"{}\" }, \"connections\" : [ { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" }, { \"componentName\" : \"componentName\", \"componentVersion\" : 6, \"key\" : \"key\" } ], \"timeout\" : \"timeout\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
