/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.8.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.bytechef.platform.configuration.web.rest;

import com.bytechef.platform.configuration.web.rest.model.GetWorkflowNodeDescription200ResponseModel;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-14T19:06:43.949922+02:00[Europe/Zagreb]", comments = "Generator version: 7.8.0")
@Validated
@Tag(name = "workflow-node-description", description = "The Platform Workflow Node Description Internal API")
public interface WorkflowNodeDescriptionApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /workflows/{id}/descriptions/{workflowNodeName} : Get an action description shown in the editor
     * Get an action description shown in the editor.
     *
     * @param id The workflow id (required)
     * @param workflowNodeName The name of an workflow&#39;s action task or trigger (E.g. mailchimp_1) (required)
     * @return The editor description. (status code 200)
     */
    @Operation(
        operationId = "getWorkflowNodeDescription",
        summary = "Get an action description shown in the editor",
        description = "Get an action description shown in the editor.",
        tags = { "workflow-node-description" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The editor description.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetWorkflowNodeDescription200ResponseModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workflows/{id}/descriptions/{workflowNodeName}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<GetWorkflowNodeDescription200ResponseModel> getWorkflowNodeDescription(
        @Parameter(name = "id", description = "The workflow id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
        @Parameter(name = "workflowNodeName", description = "The name of an workflow's action task or trigger (E.g. mailchimp_1)", required = true, in = ParameterIn.PATH) @PathVariable("workflowNodeName") String workflowNodeName
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"description\" : \"description\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
