
/*
 * Copyright 2021 <your company/name>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.cli.command.component.subcommand;

import com.bytechef.hermes.component.RestComponentHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestComponentGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RestComponentGenerator.class);

    private static final ClassName AUTHORIZATION_CLASS_NAME = ClassName.get("com.bytechef.hermes.component.definition",
        "Authorization");
    public static final String COM_BYTECHEF_HERMES_COMPONENT_PACKAGE = "com.bytechef.hermes.component";
    public static final ClassName COMPONENT_DEFINITION_CLASS_NAME = ClassName
        .get(COM_BYTECHEF_HERMES_COMPONENT_PACKAGE + ".definition", "ComponentDefinition");
    public static final ClassName COMPONENT_DSL_CLASS_NAME = ClassName
        .get(COM_BYTECHEF_HERMES_COMPONENT_PACKAGE + ".definition", "ComponentDSL");
    private static final ClassName COMPONENT_CONSTANTS_CLASS_NAME = ClassName
        .get("com.bytechef.hermes.component.constants", "ComponentConstants");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() {
        {
            enable(SerializationFeature.INDENT_OUTPUT);
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    };

    private final String basePackageName;
    private final String componentName;
    private final GeneratorConfig generatorConfig;
    private final OpenAPI openAPI;
    private final String outputPath;
    private final Set<String> schemas = new HashSet<>();
    private final boolean internalComponent;
    private final int version;

    public RestComponentGenerator(
        String basePackageName, String componentName, int version, boolean internalComponent, String openApiPath,
        String outputPath) throws IOException {

        this.basePackageName = basePackageName;
        this.componentName = componentName;
        this.version = version;
        this.internalComponent = internalComponent;
        this.openAPI = parseOpenAPIFile(openApiPath);
        this.outputPath = outputPath;

        File directory = new File(openApiPath).getParentFile();

        File configFile = new File(directory.getPath() + File.separator + "generator-config.json");

        if (configFile.exists()) {
            try (JsonParser jsonParser = OBJECT_MAPPER.createParser(configFile)) {
                generatorConfig = jsonParser.readValueAs(GeneratorConfig.class);
            }
        } else {
            generatorConfig = new GeneratorConfig();
        }
    }

    public void generate() throws Exception {
        Path sourceMainJavaDirPath = Files.createDirectories(
            Paths.get(getAbsolutePathname("src" + File.separator + "main" + File.separator + "java")));

        int schemaSize = schemas.size();

        Path componentHandlerSourcePath = writeAbstractComponentHandlerSource(sourceMainJavaDirPath);

        while (schemaSize != schemas.size()) {
            schemaSize = schemas.size();

            checkComponentSchemaSources(schemas);
        }

        writeComponentSchemaSources(schemas, sourceMainJavaDirPath);
        writeComponentHandlerSource(sourceMainJavaDirPath);

        if (internalComponent) {
            Path sourceTestJavaDirPath = Files.createDirectories(
                Paths.get(getAbsolutePathname("src" + File.separator + "test" + File.separator + "java")));

            writeAbstractComponentHandlerTest(sourceTestJavaDirPath);
            writeComponentHandlerDefinition(
                componentHandlerSourcePath, getPackageName(), getComponentHandlerClassName(componentName), version);
            writeComponentHandlerTest(sourceTestJavaDirPath);
        }

        writeComponentHandlerServiceFile();
    }

    private String capitalize(final String str) {
        final int strLen = str == null ? 0 : str.length();

        if (strLen == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);

        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint;

        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen;) {
            final int codepoint = str.codePointAt(inOffset);

            newCodePoints[outOffset++] = codepoint;
            inOffset += Character.charCount(codepoint);
        }

        return new String(newCodePoints, 0, outOffset);
    }

    @SuppressWarnings("rawtypes")
    private void checkComponentSchemaSources(Set<String> schemas) {
        Components components = openAPI.getComponents();

        if (components != null) {
            Map<String, Schema> schemaMap = components.getSchemas();

            if (schemaMap != null) {
                for (Map.Entry<String, Schema> entry : schemaMap.entrySet()) {
                    if (!schemas.contains(entry.getKey())) {
                        continue;
                    }

                    getObjectPropertiesCodeBlock(null, entry.getValue(), openAPI);
                }
            }
        }
    }

    private Path compileComponentHandlerSource(Path sourcePath, String simpleClassName) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        List<String> javacOpts = new ArrayList<>();

        javacOpts.add("-classpath");
        javacOpts.add("libs/hermes-component-api-1.0.jar:libs/hermes-definition-api-1.0.jar");

        Path parentPath = sourcePath.getParent();

        for (String dirName : new String[] {
            "action", "schema"
        }) {
            Path dirPath = parentPath.resolve(dirName);

            File dirFile = dirPath.toFile();

            File[] files = dirFile.listFiles((curDir, name) -> {
                name = name.toLowerCase();

                return name.endsWith(".java");
            });

            if (files != null) {
                for (File file : files) {
                    javacOpts.add(dirFile.getAbsolutePath() + "/" + file.getName());
                }
            }
        }

        Path tempDirPath;

        try {
            tempDirPath = Files.createTempDirectory("rest_component");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        javacOpts.add("-d");
        javacOpts.add(tempDirPath.toString());
        javacOpts.add(sourcePath.getParent() + "/Abstract" + simpleClassName + ".java");
        javacOpts.add(sourcePath.getParent() + "/" + simpleClassName + ".java");

        javaCompiler.run(null, null, null, javacOpts.toArray(new String[0]));

        return tempDirPath;
    }

    private String deleteWhitespace(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        final int sz = str.length();
        final char[] chs = new char[sz];

        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }

        if (count == sz) {
            return str;
        }

        if (count == 0) {
            return "";
        }

        return new String(chs, 0, count);
    }

    private Map<String, List<OperationItem>> filterOperationItemsMap(Map<String, List<OperationItem>> operationsMap) {
        Map<String, List<OperationItem>> filteredOperationsMap;
        List<String> operations = generatorConfig.openApi.operations;

        if (operations.isEmpty()) {
            filteredOperationsMap = operationsMap;
        } else {
            filteredOperationsMap = new LinkedHashMap<>();

            for (Map.Entry<String, List<OperationItem>> operationItemsEntry : operationsMap.entrySet()) {
                List<OperationItem> curOperationItems = operationItemsEntry.getValue();

                curOperationItems = curOperationItems.stream()
                    .filter(operationItem -> operations.stream()
                        .anyMatch(operationId -> Objects.equals(operationItem.getOperationId(), operationId)))
                    .toList();

                if (!curOperationItems.isEmpty()) {
                    filteredOperationsMap.put(operationItemsEntry.getKey(), curOperationItems);
                }
            }
        }

        return filteredOperationsMap;
    }

    private String getAbsolutePathname(String subPath) {
        return outputPath + File.separator + componentName + File.separator + subPath;
    }

    private CodeBlock getActionsCodeBlock(List<OperationItem> operationItems, OpenAPI openAPI) {
        List<CodeBlock> codeBlocks = new ArrayList<>();

        for (OperationItem operationItem : operationItems) {
            Operation operation = operationItem.operation();
            String requestMethod = operationItem.requestMethod();

            OutputEntry outputEntry = getOutputEntry(operation, openAPI);
            PropertiesEntry propertiesEntry = getPropertiesEntry(operation, openAPI);

            CodeBlock.Builder builder = CodeBlock.builder();

            CodeBlock.Builder metadataBuilder = CodeBlock.builder();

            metadataBuilder.add(
                """
                    "requestMethod", $S,
                    "path", $S
                    """,
                requestMethod,
                operationItem.path);

            if (propertiesEntry.bodyContentType() != null) {
                metadataBuilder.add(
                    """
                        ,"bodyContentType", $S
                        """,
                    propertiesEntry.bodyContentType());
            }

            builder.add(
                """
                    action($S)
                        .display(
                            display($S)
                                .description($S)
                        )
                        .metadata(
                            $T.of(
                                $L
                            )
                        )
                        .properties($L)
                    """,
                operation.getOperationId(),
                operation.getSummary(),
                operation.getDescription(),
                Map.class,
                metadataBuilder.build(),
                propertiesEntry.propertiesCodeBlock());

            CodeBlock codeBlock = outputEntry == null ? null : outputEntry.outputCodeBlock();

            if (codeBlock != null && !codeBlock.isEmpty()) {
                builder.add(".output($L)", codeBlock);
            }

            Object example = outputEntry == null ? null : outputEntry.example();

            if (example != null) {
                builder.add(".exampleOutput($S)", example);
            }

            codeBlocks.add(builder.build());
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    private CodeBlock getActionsCodeBlock(Path componentHandlerDirPath, OpenAPI openAPI) throws IOException {
        Map<String, List<OperationItem>> operationsMap = filterOperationItemsMap(
            getOperationItemsMap(openAPI.getPaths()));

        List<CodeBlock> codeBlocks = new ArrayList<>();

        for (Map.Entry<String, List<OperationItem>> operationItemsEntry : operationsMap.entrySet()) {
            CodeBlock actionsCodeBlock = getActionsCodeBlock(operationItemsEntry.getValue(), openAPI);

            if (generatorConfig.openApi.useTags) {
                String key = operationItemsEntry.getKey();

                String prefix = Arrays.stream(key.split(" "))
                    .map(this::capitalize)
                    .collect(Collectors.joining());

                ClassName className = ClassName.get(getPackageName() + ".action", prefix + "Actions");

                writeComponentActionsSource(className, actionsCodeBlock, componentHandlerDirPath);

                codeBlocks.add(CodeBlock.of("$T.ACTIONS", className));
            } else {
                codeBlocks.add(actionsCodeBlock);
            }
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    private CodeBlock getAuthorizationApiKeyCodeBlock(SecurityScheme securityScheme) {
        CodeBlock.Builder builder = CodeBlock.builder();

        SecurityScheme.In in = securityScheme.getIn();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.API_KEY.name().toLowerCase(),
                    $T.AuthorizationType.API_KEY
                )
                .display(
                    display($S)
                )
                .properties(
                    string(KEY)
                        .label($S)
                        .required($L)
                        .defaultValue($L)
                        .hidden($L),
                    string(VALUE)
                        .label($S)
                        .required($L),
                    string(ADD_TO)
                        .label($S)
                        .required($L)
                        .defaultValue($L)
                        .hidden($L)
                )
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "API Key",
            "Key",
            true,
            securityScheme.getName() == null
                ? CodeBlock.of("$L", "API_TOKEN")
                : CodeBlock.of("$S", securityScheme.getName()),
            true,
            "Value",
            true,
            "Add to",
            true,
            in == SecurityScheme.In.HEADER
                ? CodeBlock.of("$T.ApiTokenLocation.HEADER.name()", AUTHORIZATION_CLASS_NAME)
                : CodeBlock.of("$T.ApiTokenLocation.QUERY_PARAMETERS.name()", AUTHORIZATION_CLASS_NAME),
            true);

        return builder.build();
    }

    private CodeBlock getAuthorizationBasicCodeBlock() {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.BASIC_AUTH.name().toLowerCase(),
                    $T.AuthorizationType.BASIC_AUTH
                )
                .display(
                    display($S)
                )
                .properties(
                    string(USERNAME)
                        .label($S)
                        .required($L),
                    string(PASSWORD)
                        .label($S)
                        .required($L)
                )
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "Basic Auth",
            "Username",
            true,
            "Password",
            true);

        return builder.build();
    }

    private CodeBlock getAuthorizationBearerCodeBlock() {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.BEARER_TOKEN.name().toLowerCase(),
                    $T.AuthorizationType.BEARER_TOKEN
                )
                .display(
                    display($S)
                )
                .properties(
                    string(TOKEN)
                        .label($S)
                        .required($L)
                )
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "Bearer Token",
            "Token",
            true);

        return builder.build();
    }

    private CodeBlock getAuthorizationOAuth2AuthorizationCodeCodeBlock(OAuthFlow oAuthFlow) {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.OAUTH2_AUTHORIZATION_CODE.name().toLowerCase(),
                    $T.AuthorizationType.OAUTH2_AUTHORIZATION_CODE
                )
                .display(
                    display($S)
                )
                .properties(
                    string(CLIENT_ID)
                        .label($S)
                        .required($L),
                    string(CLIENT_SECRET)
                        .label($S)
                        .required($L)
                )
                .authorizationUrl(connection -> $S)
                .refreshUrl(connection -> $S)
                .scopes(connection -> $T.of($L))
                .tokenUrl(connection -> $S)
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "OAuth2 Authorization Code",
            "Client Id",
            true,
            "Client Secret",
            true,
            oAuthFlow.getAuthorizationUrl(),
            oAuthFlow.getRefreshUrl(),
            List.class,
            getOAUth2Scopes(oAuthFlow.getScopes()),
            oAuthFlow.getTokenUrl());

        return builder.build();
    }

    private CodeBlock getAuthorizationOAuth2ClientCredentialsCodeBlock(OAuthFlow oAuthFlow) {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.OAUTH2_CLIENT_CREDENTIALS.name().toLowerCase(),
                    $T.AuthorizationType.OAUTH2_CLIENT_CREDENTIALS
                )
                .display(
                    display($S)
                )
                .properties(
                    string(CLIENT_ID)
                        .label($S)
                        .required($L),
                    string(CLIENT_SECRET)
                        .label($S)
                        .required($L)
                )
                .refreshUrl(connection -> $S)
                .scopes(connection -> $T.of($L))
                .tokenUrl(connection -> $S)
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "Client Credentials",
            "Client Id",
            true,
            "OAuth2 Client Secret",
            true,
            List.class,
            oAuthFlow.getRefreshUrl(),
            getOAUth2Scopes(oAuthFlow.getScopes()),
            oAuthFlow.getTokenUrl());

        return builder.build();
    }

    private CodeBlock getAuthorizationOAuth2ImplicitCodeBlock(OAuthFlow oAuthFlow) {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.OAUTH2_IMPLICIT_CODE.name().toLowerCase(),
                    $T.AuthorizationType.OAUTH2_IMPLICIT_CODE
                )
                .display(
                    display($S)
                )
                .properties(
                    string(CLIENT_ID)
                        .label($S)
                        .required($L),
                    string(CLIENT_SECRET)
                        .label($S)
                        .required($L)
                )
                .authorizationUrl(connection -> $S)
                .refreshUrl(connection -> $S)
                .scopes(connection -> $T.of($L))
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "OAuth2 Implicit",
            "Client Id",
            true,
            "Client Secret",
            true,
            oAuthFlow.getAuthorizationUrl(),
            oAuthFlow.getRefreshUrl(),
            List.class,
            getOAUth2Scopes(oAuthFlow.getScopes()));

        return builder.build();
    }

    private CodeBlock getAuthorizationOAuth2PasswordCodeBlock(OAuthFlow oAuthFlow) {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                authorization(
                    $T.AuthorizationType.OAUTH2_RESOURCE_OWNER_PASSWORD.name().toLowerCase(),
                    $T.AuthorizationType.OAUTH2_RESOURCE_OWNER_PASSWORD
                )
                .display(
                    display($S)
                )
                .properties(
                    string(CLIENT_ID)
                        .label($S)
                        .required($L),
                    string(CLIENT_SECRET)
                        .label($S)
                        .required($L)
                )
                .refreshUrl(connection -> $S)
                .scopes(connection -> $T.of($L))
                .tokenUrl(connection -> $S)
                """,
            AUTHORIZATION_CLASS_NAME,
            AUTHORIZATION_CLASS_NAME,
            "OAuth2 Resource Owner Password",
            "Client Id",
            true,
            "Client Secret",
            true,
            List.class,
            oAuthFlow.getRefreshUrl(),
            getOAUth2Scopes(oAuthFlow.getScopes()),
            oAuthFlow.getTokenUrl());

        return builder.build();
    }

    private CodeBlock getAuthorizationsCodeBlock(Map<String, SecurityScheme> securitySchemeMap) {
        List<CodeBlock> codeBlocks = new ArrayList<>();

        if (securitySchemeMap == null || securitySchemeMap.isEmpty()) {
            codeBlocks.add(CodeBlock.of("null"));
        } else {
            for (Map.Entry<String, SecurityScheme> entry : securitySchemeMap.entrySet()) {
                SecurityScheme securityScheme = entry.getValue();

                if (securityScheme.getType() == SecurityScheme.Type.APIKEY) {
                    codeBlocks.add(getAuthorizationApiKeyCodeBlock(securityScheme));
                } else if (securityScheme.getType() == SecurityScheme.Type.HTTP) {
                    String scheme = securityScheme.getScheme();

                    if (Objects.equals(scheme, "basic")) {
                        codeBlocks.add(getAuthorizationBasicCodeBlock());
                    } else if (Objects.equals(scheme, "bearer")) {
                        codeBlocks.add(getAuthorizationBearerCodeBlock());
                    } else {
                        throw new IllegalStateException("Security scheme %s not supported: ".formatted(scheme));
                    }
                } else if (securityScheme.getType() == SecurityScheme.Type.OAUTH2) {
                    OAuthFlows flows = securityScheme.getFlows();

                    OAuthFlow oAuthFlow = flows.getAuthorizationCode();

                    if (oAuthFlow != null) {
                        codeBlocks.add(getAuthorizationOAuth2AuthorizationCodeCodeBlock(oAuthFlow));
                    }

                    oAuthFlow = flows.getClientCredentials();

                    if (oAuthFlow != null) {
                        codeBlocks.add(getAuthorizationOAuth2ClientCredentialsCodeBlock(oAuthFlow));
                    }

                    oAuthFlow = flows.getImplicit();

                    if (oAuthFlow != null) {
                        codeBlocks.add(getAuthorizationOAuth2ImplicitCodeBlock(oAuthFlow));
                    }

                    oAuthFlow = flows.getPassword();

                    if (oAuthFlow != null) {
                        codeBlocks.add(getAuthorizationOAuth2PasswordCodeBlock(oAuthFlow));
                    }
                } else {
                    throw new IllegalStateException(
                        "Security scheme type %s not supported: ".formatted(securityScheme.getType()));
                }
            }
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private Map<String, Schema> getAllOfSchemaProperties(String name, String description, List<Schema> allOfSchemas) {
        Map<String, Schema> allOfProperties = new HashMap<>();

        for (Schema allOfSchema : allOfSchemas) {
            if (allOfSchema.getProperties() != null || allOfSchema.getAllOf() != null) {
                if (allOfSchema.getProperties() != null) {
                    allOfProperties.putAll(allOfSchema.getProperties());
                }

                if (allOfSchema.getAllOf() != null) {
                    allOfProperties.putAll(getAllOfSchemaProperties(name, description, allOfSchema.getAllOf()));
                }
            } else {
                allOfProperties.put(name, allOfSchema.description(description));
            }
        }

        return allOfProperties;
    }

    private static CodeBlock getBaseUriCodeBlock(List<Server> servers) {
        CodeBlock.Builder builder = CodeBlock.builder();

        if (servers == null || servers.isEmpty()) {
            builder.add("null");
        } else {
            if (servers.size() == 1) {
                Server server = servers.get(0);

                builder.add(".baseUri(connection -> $S)", server.getUrl());
            } else {
                List<CodeBlock> codeBlocks = new ArrayList<>();

                for (Server server : servers) {
                    codeBlocks.add(CodeBlock.of("option($L)", server.getUrl()));
                }

                builder.add(
                    """
                        .properties(
                            string(BASE_URI)
                                .label($S)
                                .options($L)"
                        """,
                    "Base URI",
                    codeBlocks.stream()
                        .collect(CodeBlock.joining(",")));

                Server server = servers.get(0);

                builder.add(".defaultValue($S)", server.getUrl());
                builder.add(")");
            }
        }

        return builder.build();
    }

    private CodeBlock getComponentCodeBlock(Path componentHandlerDirPath) throws IOException {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add(
            """
                component($S)
                    .display(
                        display($S)
                        .description($S)
                    )
                    .actions($L)
                """,
            componentName,
            capitalize(componentName),
            openAPI.getInfo()
                .getDescription(),
            getActionsCodeBlock(componentHandlerDirPath, openAPI));

        CodeBlock codeBlock = getConnectionCodeBlock(openAPI);

        if (!codeBlock.isEmpty()) {
            builder.add(".connection($L)", codeBlock);
        }

        return builder.build();
    }

    private String getComponentHandlerClassName(String componentName) {
        return capitalize(componentName) + "ComponentHandler";
    }

    private CodeBlock getConnectionCodeBlock(OpenAPI openAPI) {
        CodeBlock.Builder builder = CodeBlock.builder();

        Components components = openAPI.getComponents();

        if (components != null) {
            Map<String, SecurityScheme> securitySchemeMap = components.getSecuritySchemes();
            List<Server> servers = openAPI.getServers();

            if ((securitySchemeMap == null || securitySchemeMap.isEmpty()) && (servers == null || servers.isEmpty())) {
                builder.add("null");
            } else {
                builder.add(
                    """
                        connection()
                            $L
                            .authorizations($L)
                        """,
                    getBaseUriCodeBlock(servers),
                    getAuthorizationsCodeBlock(securitySchemeMap));
            }
        }

        return builder.build();
    }

    private CodeBlock getObjectPropertiesCodeBlock(String name, Schema<?> schema, OpenAPI openAPI) {
        List<CodeBlock> codeBlocks = new ArrayList<>();

        if (schema.getProperties() != null) {
            codeBlocks.add(getPropertiesSchemaCodeBlock(
                schema.getProperties(), schema.getRequired() == null ? List.of() : schema.getRequired(), openAPI));
        }

        if (schema.getAllOf() != null) {
            codeBlocks.add(getAllOfSchemaCodeBlock(name, schema.getDescription(), schema.getAllOf(), openAPI));
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    private String getOAUth2Scopes(Scopes scopes) {
        Collection<String> scopeNames;

        if (generatorConfig.openApi.oAuth2Scopes.isEmpty()) {
            scopeNames = scopes.keySet();
        } else {
            scopeNames = generatorConfig.openApi.oAuth2Scopes;
        }

        return String.join(
            ",", scopeNames.stream()
                .map(scope -> "\"" + scope + "\"")
                .toList());
    }

    private Map<String, List<OperationItem>> getOperationItemsMap(io.swagger.v3.oas.models.Paths paths) {
        Map<String, List<OperationItem>> operationItemsMap = new LinkedHashMap<>();

        List<OperationItem> operationItems = new ArrayList<>();

        for (Map.Entry<String, PathItem> pathEntry : paths.entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            String path = pathEntry.getKey();

            if (pathItem.getDelete() != null) {
                operationItems.add(new OperationItem(pathItem.getDelete(), "DELETE", path));
            }

            if (pathItem.getHead() != null) {
                operationItems.add(new OperationItem(pathItem.getHead(), "HEAD", path));
            }

            if (pathItem.getGet() != null) {
                operationItems.add(new OperationItem(pathItem.getGet(), "GET", path));
            }

            if (pathItem.getPatch() != null) {
                operationItems.add(new OperationItem(pathItem.getPatch(), "PATCH", path));
            }

            if (pathItem.getPost() != null) {
                operationItems.add(new OperationItem(pathItem.getPost(), "POST", path));
            }

            if (pathItem.getPut() != null) {
                operationItems.add(new OperationItem(pathItem.getPut(), "PUT", path));
            }
        }

        for (OperationItem operationItem : operationItems) {
            List<String> tags = operationItem.operation()
                .getTags();

            String tag = "unnamed";

            if (tags != null && !tags.isEmpty()) {
                tag = tags.get(0);
            }

            operationItemsMap.compute(tag, (key, tagOperationItems) -> {
                if (tagOperationItems == null) {
                    tagOperationItems = new ArrayList<>();
                }

                if (tagOperationItems.stream()
                    .noneMatch(curOperationItem -> Objects.equals(operationItem.getOperationId(),
                        curOperationItem.getOperationId()))) {
                    tagOperationItems.add(operationItem);
                }

                return tagOperationItems;
            });
        }

        return operationItemsMap;
    }

    @SuppressWarnings({
        "rawtypes"
    })
    private OutputEntry getOutputEntry(Operation operation, OpenAPI openAPI) {
        ApiResponse apiResponse = null;
        ApiResponses apiResponses = operation.getResponses();
        CodeBlock.Builder builder = CodeBlock.builder();
        OutputEntry outputEntry = null;

        for (String responseCode : List.of("200", "201")) {
            apiResponse = apiResponses.get(responseCode);

            if (apiResponse != null) {
                break;
            }
        }

        if (apiResponse != null && apiResponse.getContent() != null) {
            Content content = apiResponse.getContent();

            Set<Map.Entry<String, MediaType>> entries = content.entrySet();

            if (!entries.isEmpty()) {

                // Use the first response type

                String key = entries.iterator()
                    .next()
                    .getKey();

                String responseType = switch (key) {
                    case "application/json" -> "JSON";
                    case "application/xml" -> "XML";
                    default -> "TEXT";
                };

                MediaType mediaType = content.get(key);

                Schema schema = mediaType.getSchema();

                builder.add(getSchemaCodeBlock(null, schema.getDescription(), null, null, schema, openAPI, true));
                builder.add(
                    """
                        .metadata(
                           $T.of(
                             "responseType", $S
                           )
                        )
                        """,
                    Map.class,
                    responseType);

                outputEntry = new OutputEntry(builder.build(), mediaType.getExample());
            }
        }

        return outputEntry;
    }

    private String getPackageName() {
        return deleteWhitespace(basePackageName == null ? "" : basePackageName + ".") +
            replaceChars(componentName, "-_", ".");
    }

    private CodeBlock getParametersPropertiesCodeBlock(Operation operation, OpenAPI openAPI) {
        List<CodeBlock> codeBlocks = new ArrayList<>();
        List<Parameter> parameters = operation.getParameters();

        if (parameters != null) {
            for (Parameter parameter : parameters) {
                CodeBlock.Builder builder = CodeBlock.builder();

                builder.add(getSchemaCodeBlock(
                    parameter.getName(), parameter.getDescription(), parameter.getRequired(), null,
                    parameter.getSchema(), openAPI, false));
                builder.add(CodeBlock.of(
                    """
                        .metadata(
                           $T.of(
                             "type", $S
                           )
                        )
                        """,
                    Map.class,
                    parameter.getIn()
                        .toUpperCase()));

                codeBlocks.add(builder.build());
            }
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    private PropertiesEntry getPropertiesEntry(Operation operation, OpenAPI openAPI) {
        List<CodeBlock> codeBlocks = new ArrayList<>();

        CodeBlock codeBlock = getParametersPropertiesCodeBlock(operation, openAPI);

        if (!codeBlock.isEmpty()) {
            codeBlocks.add(codeBlock);
        }

        RequestBodyPropertiesEntry requestBodyPropertiesEntry = getRequestBodyPropertiesItem(operation, openAPI);

        if (requestBodyPropertiesEntry != null) {
            codeBlock = requestBodyPropertiesEntry.requestBodyPropertiesCodeBlock();

            if (!codeBlock.isEmpty()) {
                codeBlocks.add(codeBlock);
            }
        }

        return new PropertiesEntry(
            codeBlocks.stream()
                .collect(CodeBlock.joining(",")),
            requestBodyPropertiesEntry == null ? null : requestBodyPropertiesEntry.bodyContentType);
    }

    @SuppressWarnings({
        "rawtypes"
    })
    private RequestBodyPropertiesEntry getRequestBodyPropertiesItem(Operation operation, OpenAPI openAPI) {
        CodeBlock.Builder builder = CodeBlock.builder();
        RequestBody requestBody = operation.getRequestBody();
        RequestBodyPropertiesEntry requestBodyPropertiesEntry = null;

        if (requestBody != null) {
            Content content = requestBody.getContent();

            String bodyContentType = null;
            Set<Map.Entry<String, MediaType>> entries = content.entrySet();

            if (!entries.isEmpty()) {

                // Use the first body content type

                String key = entries.iterator()
                    .next()
                    .getKey();

                // CHECKSTYLE:OFF
                bodyContentType = switch (key) {
                    case "application/json" -> "JSON";
                    case "application/xml" -> "XML";
                    case "application/x-www-form-urlencoded" -> "FORM_URLENCODED";
                    case "application/octet-stream" -> "BINARY";
                    case "multipart/form-data" -> "FORM_DATA";
                    default -> throw new IllegalArgumentException(
                        String.format("Media type %s is not supported.", key));
                };
                // CHECKSTYLE:ON

                MediaType mediaType = content.get(key);

                Schema schema = mediaType.getSchema();

                builder.add(getSchemaCodeBlock(null, null, requestBody.getRequired(), null, schema, openAPI, false));
                builder.add(
                    """
                        .metadata(
                           $T.of(
                             "type", $S
                           )
                        )
                        """,
                    Map.class,
                    "BODY");
            }

            requestBodyPropertiesEntry = new RequestBodyPropertiesEntry(builder.build(), bodyContentType);
        }

        return requestBodyPropertiesEntry;
    }

    private CodeBlock getAdditionalPropertiesCodeBlock(String propertyName, Schema<?> schema, boolean outputEntry) {
        CodeBlock.Builder builder = CodeBlock.builder();

        if (schema.getAdditionalProperties() instanceof Boolean) {
            builder.add(
                "object($S).additionalProperties($L)",
                propertyName,
                schema.getAdditionalProperties());
        } else {
            Schema<?> additionalPropertiesSchema = (Schema<?>) schema.getAdditionalProperties();

            if (additionalPropertiesSchema.get$ref() == null) {
                builder.add(
                    "object($S).additionalProperties($L())",
                    propertyName,
                    getAdditionalPropertiesItemType(additionalPropertiesSchema));
            } else {
                String ref = additionalPropertiesSchema.get$ref();

                String curSchemaName = ref.replace("#/components/schemas/", "");

                schemas.add(curSchemaName);

                builder.add(
                    "object($S).additionalProperties(object().properties($L))",
                    propertyName,
                    CodeBlock.of(
                        "$T.COMPONENT_SCHEMA",
                        ClassName.get(getPackageName() + ".schema", curSchemaName + "Schema")));
            }
        }

        if (!outputEntry) {
            builder.add(".placeholder($S)", "Add");
        }

        return builder.build();
    }

    @SuppressWarnings("rawtypes")
    private String getAdditionalPropertiesItemType(Schema additionalPropertiesSchema) {
        String additionalPropertiesSchemaType = additionalPropertiesSchema.getType() == null ? "object"
            : additionalPropertiesSchema.getType();

        return switch (additionalPropertiesSchemaType) {
            case "array" -> "array";
            case "boolean" -> "bool";
            case "integer" -> "integer";
            case "number" -> "number";
            case "object" -> "object";
            case "string" -> {
                if (additionalPropertiesSchema.getFormat() == null) {
                    yield "string";
                } else if (Objects.equals(additionalPropertiesSchema.getFormat(), "date")) {
                    yield "date";
                } else if (Objects.equals(additionalPropertiesSchema.getFormat(), "date-date")) {
                    yield "date-time";
                } else {
                    throw new IllegalArgumentException(
                        "Unsupported schema type format: " + additionalPropertiesSchema.getFormat());
                }
            }
            default -> throw new IllegalArgumentException(
                "Unsupported schema type: " + additionalPropertiesSchema.getType());
        };
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private CodeBlock getAllOfSchemaCodeBlock(
        String name, String description, List<Schema> allOfSchemas, OpenAPI openAPI) {

        Map<String, Schema> allOfProperties = getAllOfSchemaProperties(name, description, allOfSchemas);
        List<String> allOfRequired = new ArrayList<>();

        for (Schema allOfSchema : allOfProperties.values()) {
            if (allOfSchema.getRequired() != null) {
                allOfRequired.addAll(allOfSchema.getRequired());
            }
        }

        return getPropertiesSchemaCodeBlock(allOfProperties, allOfRequired, openAPI);
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private CodeBlock getPropertiesSchemaCodeBlock(
        Map<String, Schema> properties, List<String> required, OpenAPI openAPI) {
        List<CodeBlock> codeBlocks = new ArrayList<>();

        for (Map.Entry<String, Schema> entry : properties.entrySet()) {
            CodeBlock codeBlock;
            Schema schema = entry.getValue();

            if (schema.getAllOf() == null) {
                codeBlock = getSchemaCodeBlock(
                    entry.getKey(), schema.getDescription(), required.contains(entry.getKey()), null, schema, openAPI,
                    false);
            } else {
                codeBlock = getAllOfSchemaCodeBlock(entry.getKey(), schema.getDescription(), schema.getAllOf(),
                    openAPI);
            }

            if (codeBlock.isEmpty()) {
                throw new IllegalStateException("Schema is not supported: " + schema);
            } else {
                codeBlocks.add(codeBlock);
            }
        }

        return codeBlocks.stream()
            .collect(CodeBlock.joining(","));
    }

    @SuppressWarnings({
        "rawtypes"
    })
    private CodeBlock getSchemaCodeBlock(
        String propertyName, String propertyDescription, Boolean required, String schemaName, Schema<?> schema,
        OpenAPI openAPI, boolean outputEntry) {

        CodeBlock.Builder builder = CodeBlock.builder();

        if (schema.get$ref() == null) {
            String type = schema.getType() == null ? "object" : schema.getType();

            switch (type) {
                case "array" -> {
                    builder.add(
                        "array($S).items($L)",
                        propertyName,
                        getSchemaCodeBlock(
                            schema.getTitle(), schema.getDescription(), null, null, schema.getItems(), openAPI, false));

                    if (!outputEntry) {
                        builder.add(".placeholder($S)", "Add");
                    }
                }
                case "boolean" -> builder.add("bool($S)", propertyName);
                case "integer" -> {
                    builder.add("integer($S)", propertyName);
                    if (schema.getMinimum() != null) {
                        BigDecimal minimum = schema.getMinimum();

                        builder.add(".minValue($L)", minimum.intValue());
                    }
                    if (schema.getMaximum() != null) {
                        BigDecimal maximum = schema.getMaximum();

                        builder.add(".maxValue($L)", maximum.intValue());
                    }
                }
                case "number" -> {
                    builder.add("number($S)", propertyName);
                    if (schema.getMinimum() != null) {
                        BigDecimal minimum = schema.getMinimum();

                        builder.add(".minValue($L)", minimum.doubleValue());
                    }
                    if (schema.getMaximum() != null) {
                        BigDecimal maximum = schema.getMaximum();

                        builder.add(".maxValue($L)", maximum.doubleValue());
                    }
                }
                case "object" -> {
                    if (schema.getProperties() != null || schema.getAllOf() != null) {
                        CodeBlock propertiesCodeBlock;

                        if (schemas.contains(schemaName)) {
                            propertiesCodeBlock = CodeBlock.of(
                                "$T.COMPONENT_SCHEMA",
                                ClassName.get(getPackageName() + ".schema", schemaName + "Schema"));
                        } else {
                            propertiesCodeBlock = getObjectPropertiesCodeBlock(propertyName, schema, openAPI);
                        }

                        builder.add("object($S).properties($L)", propertyName, propertiesCodeBlock);
                    } else if (schema.getAdditionalProperties() != null) {
                        builder.add(getAdditionalPropertiesCodeBlock(propertyName, schema, outputEntry));
                    } else {
                        builder.add("object($S)", propertyName);
                    }
                }
                case "string" -> {
                    if (Objects.equals(schema.getFormat(), "date")) {
                        builder.add("date($S)", propertyName);
                    } else if (Objects.equals(schema.getFormat(), "date-time")) {
                        builder.add("dateTime($S)", propertyName);
                    } else {
                        builder.add("string($S)", propertyName);
                    }
                }
                default -> throw new IllegalArgumentException(
                    "Parameter type %s is not supported.".formatted(schema.getType()));
            }

            if (propertyName != null) {
                builder.add(".label($S)", capitalize(propertyName));
            }

            if (propertyDescription != null) {
                builder.add(".description($S)", propertyDescription);
            }

            if (schema.getEnum() != null) {
                List<?> enums = schema.getEnum()
                    .stream()
                    .filter(Objects::nonNull)
                    .toList();
                List<CodeBlock> codeBlocks = new ArrayList<>();

                for (Object item : enums) {
                    if (item instanceof String) {
                        codeBlocks.add(CodeBlock.of("option($S, $S)", capitalize(item.toString()), item));
                    } else {
                        codeBlocks.add(CodeBlock.of("option($S, $L)", capitalize(item.toString()), item));
                    }
                }

                builder.add(".options($L)", codeBlocks.stream()
                    .collect(CodeBlock.joining(",")));
            }

            if (required != null) {
                builder.add(".required($L)", required);
            }

            if (schema.getExample() != null) {
                if (Objects.equals(type, "string")) {
                    builder.add(".exampleValue($S)", schema.getExample());
                } else {
                    builder.add(".exampleValue($L)", schema.getExample());
                }
            }
        } else {
            String ref = schema.get$ref();
            Components components = openAPI.getComponents();

            Map<String, Schema> schemaMap = components.getSchemas();

            String curSchemaName = ref.replace("#/components/schemas/", "");

            schemas.add(curSchemaName);

            schema = schemaMap.get(curSchemaName);

            builder.add(getSchemaCodeBlock(
                propertyName, schema.getDescription(), required, curSchemaName, schema, openAPI, false));
        }

        return builder.build();
    }

    private OpenAPI parseOpenAPIFile(String openApiPath) {
        SwaggerParseResult result = new OpenAPIParser().readLocation(openApiPath, null, null);

        OpenAPI openAPI = result.getOpenAPI();

        if (result.getMessages() != null) {
            List<String> messages = result.getMessages();

            messages.forEach(logger::error);
        }

        return openAPI;
    }

    private String replaceChars(final String str, final String searchChars, String replaceChars) {
        if (str == null || str.isEmpty() || searchChars == null || searchChars.isEmpty()) {
            return str;
        }

        if (replaceChars == null) {
            replaceChars = "";
        }

        boolean modified = false;
        final int replaceCharsLength = replaceChars.length();
        final int strLength = str.length();

        final StringBuilder buf = new StringBuilder(strLength);

        for (int i = 0; i < strLength; i++) {
            final char ch = str.charAt(i);
            final int index = searchChars.indexOf(ch);

            if (index >= 0) {
                modified = true;

                if (index < replaceCharsLength) {
                    buf.append(replaceChars.charAt(index));
                }
            } else {
                buf.append(ch);
            }
        }

        if (modified) {
            return buf.toString();
        }

        return str;
    }

    private RestComponentHandler runComponentHandlerClass(Path classPath, String className) throws Exception {
        File classFile = classPath.toFile();

        URI classURI = classFile.toURI();

        URL[] classUrls = new URL[] {
            classURI.toURL()
        };

        URLClassLoader classLoader = URLClassLoader.newInstance(classUrls);

        @SuppressWarnings("unchecked")
        Class<RestComponentHandler> clazz = (Class<RestComponentHandler>) Class.forName(className, true, classLoader);

        return clazz.getDeclaredConstructor()
            .newInstance();
    }

    private Path writeAbstractComponentHandlerSource(Path sourceDirPath) throws IOException {
        JavaFile javaFile = JavaFile.builder(
            getPackageName(),
            TypeSpec.classBuilder("Abstract" + getComponentHandlerClassName(componentName))
                .addJavadoc("""
                    Provides the base implementation for the REST based component.

                    @generated
                    """)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addSuperinterface(
                    ClassName.get(COM_BYTECHEF_HERMES_COMPONENT_PACKAGE, "RestComponentHandler"))
                .addField(FieldSpec.builder(COMPONENT_DEFINITION_CLASS_NAME, "componentDefinition")
                    .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                    .initializer(getComponentCodeBlock(sourceDirPath))
                    .build())
                .addMethod(MethodSpec.methodBuilder("getDefinition")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(COMPONENT_DEFINITION_CLASS_NAME)
                    .addStatement("return componentDefinition")
                    .build())
                .build())
            .addStaticImport(AUTHORIZATION_CLASS_NAME, "ApiTokenLocation", "AuthorizationType")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ACCESS_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ADD_TO")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "API_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "AUTHORIZATION_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "BASE_URI")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_ID")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_SECRET")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "HEADER_PREFIX")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "KEY")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "PASSWORD")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "REFRESH_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "SCOPES")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "USERNAME")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "VALUE")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "authorization")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "component")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "connection")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "display")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "array")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "action")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "bool")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "date")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "dateTime")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "display")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "integer")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "number")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "object")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "option")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "string")
            .build();

        return javaFile.writeToPath(sourceDirPath);
    }

    private void writeAbstractComponentHandlerTest(Path testDirPath) throws IOException {
        String componentHandlerClassName = getComponentHandlerClassName(componentName);

        JavaFile javaFile = JavaFile.builder(
            getPackageName(),
            TypeSpec.classBuilder("Abstract" + componentHandlerClassName + "Test")
                .addJavadoc("""
                    Provides the base test implementation for the REST based component.

                    @generated
                    """)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(MethodSpec.methodBuilder("testGetDefinition")
                    .addAnnotation(ClassName.get("org.junit.jupiter.api", "Test"))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement(
                        "$T.assertEquals(\"definition/pipedrive_v1.json\", new $T().getDefinition())",
                        ClassName.get("com.bytechef.test.jsonasssert", "JsonFileAssert"),
                        ClassName.get(getPackageName(),
                            componentHandlerClassName))
                    .build())
                .build())
            .build();

        javaFile.writeToPath(testDirPath);
    }

    private void writeComponentActionsSource(
        ClassName className, CodeBlock actionsCodeBlock, Path componentHandlerDirPath) throws IOException {

        JavaFile javaFile = JavaFile.builder(
            className.packageName(),
            TypeSpec.classBuilder(className.simpleName())
                .addJavadoc("""
                    Provides a list of the component actions.

                    @generated
                    """)
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(
                    ParameterizedTypeName.get(
                        ClassName.get("java.util", "List"),
                        ClassName.get(
                            "com.bytechef.hermes.component.definition",
                            "ComponentDSL",
                            "ModifiableActionDefinition")),
                    "ACTIONS")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$T.of($L)", List.class, actionsCodeBlock)
                    .build())
                .build())
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ACCESS_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ADD_TO")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "API_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "AUTHORIZATION_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "BASE_URI")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_ID")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_SECRET")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "HEADER_PREFIX")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "KEY")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "PASSWORD")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "REFRESH_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "SCOPES")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "USERNAME")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "VALUE")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "array")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "action")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "bool")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "date")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "dateTime")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "display")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "integer")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "number")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "object")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "option")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "string")
            .build();

        javaFile.writeTo(componentHandlerDirPath);
    }

    private void writeComponentHandlerServiceFile() throws IOException {
        String servicesDirPathname = getAbsolutePathname(
            "src" + File.separator + "main" + File.separator + "resources" + File.separator + "META-INF" +
                File.separator + "services");

        Files.createDirectories(Paths.get(servicesDirPathname));

        String serviceName = COM_BYTECHEF_HERMES_COMPONENT_PACKAGE + ".RestComponentHandler";

        try (PrintWriter printWriter = new PrintWriter(
            servicesDirPathname + File.separator + serviceName, StandardCharsets.UTF_8)) {

            printWriter.println(getPackageName() + "." + getComponentHandlerClassName(componentName));
        }
    }

    private void writeComponentHandlerSource(Path sourceDirPath) throws IOException {
        String packageName = getPackageName();

        String filename = sourceDirPath
            .resolve(packageName.replace(".", File.separator))
            .resolve(getComponentHandlerClassName(componentName) + ".java")
            .toFile()
            .getAbsolutePath();

        if (!new File(filename).exists()) {
            JavaFile javaFile = JavaFile.builder(
                getPackageName(),
                TypeSpec.classBuilder(getComponentHandlerClassName(componentName))
                    .addJavadoc("@generated")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(
                        getPackageName(),
                        "Abstract" + getComponentHandlerClassName(componentName)))
                    .build())
                .build();
            javaFile.writeToPath(sourceDirPath);
        }
    }

    private void writeComponentHandlerTest(Path testDirPath) throws IOException {
        String packageName = getPackageName();

        String componentHandlerTestClassname = getComponentHandlerClassName(componentName) + "Test";

        String filename = testDirPath
            .resolve(packageName.replace(".", File.separator))
            .resolve(componentHandlerTestClassname + ".java")
            .toFile()
            .getAbsolutePath();

        if (!new File(filename).exists()) {
            JavaFile javaFile = JavaFile.builder(
                getPackageName(),
                TypeSpec.classBuilder(componentHandlerTestClassname)
                    .addJavadoc("@generated")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(
                        getPackageName(),
                        "Abstract" + componentHandlerTestClassname))
                    .build())
                .build();
            javaFile.writeToPath(testDirPath);
        }
    }

    private void writeComponentHandlerDefinition(
        Path componentHandlerSourcePath, String packageName, String simpleClassName, int version) throws Exception {

        Path definitionDirPath = Files.createDirectories(
            Paths.get(getAbsolutePathname("src" + File.separator + "test" + File.separator + "resources")
                + File.separator + "definition"));

        Path defintionFilePath = definitionDirPath.resolve(componentName + "_v" + version + ".json");

        File definitionFile = defintionFilePath.toFile();

        if (!definitionFile.exists()) {
            Path classPath = compileComponentHandlerSource(componentHandlerSourcePath, simpleClassName);

            RestComponentHandler restComponentHandler = runComponentHandlerClass(
                classPath, packageName + "." + simpleClassName);

            OBJECT_MAPPER.writeValue(definitionFile, restComponentHandler.getDefinition());
        }
    }

    private void writeComponentSchemaSource(
        ClassName className, CodeBlock componentSchemaCodeBlock, Path componentHandlerDirPath) throws IOException {

        JavaFile javaFile = JavaFile.builder(
            className.packageName(),
            TypeSpec.classBuilder(className.simpleName())
                .addJavadoc("""
                    Provides schema definition.

                    @generated
                    """)
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(
                    ParameterizedTypeName.get(
                        ClassName.get("java.util", "List"),
                        ClassName.get("com.bytechef.hermes.definition", "Property")),
                    "COMPONENT_SCHEMA")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$T.of($L)", List.class, componentSchemaCodeBlock)
                    .build())
                .build())
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ACCESS_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "ADD_TO")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "API_TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "AUTHORIZATION_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "BASE_URI")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_ID")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "CLIENT_SECRET")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "HEADER_PREFIX")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "KEY")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "PASSWORD")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "REFRESH_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "SCOPES")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "TOKEN_URL")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "USERNAME")
            .addStaticImport(COMPONENT_CONSTANTS_CLASS_NAME, "VALUE")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "array")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "action")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "bool")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "date")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "dateTime")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "display")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "integer")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "number")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "object")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "option")
            .addStaticImport(COMPONENT_DSL_CLASS_NAME, "string")
            .build();

        javaFile.writeTo(componentHandlerDirPath);
    }

    @SuppressWarnings("rawtypes")
    private void writeComponentSchemaSources(Set<String> schemas, Path sourceDirPath) throws IOException {
        Components components = openAPI.getComponents();

        if (components != null) {
            Map<String, Schema> schemaMap = components.getSchemas();

            if (schemaMap != null) {
                for (Map.Entry<String, Schema> entry : schemaMap.entrySet()) {
                    if (!schemas.contains(entry.getKey())) {
                        continue;
                    }

                    ClassName className = ClassName.get(getPackageName() + ".schema", entry.getKey() + "Schema");

                    writeComponentSchemaSource(
                        className,
                        getObjectPropertiesCodeBlock(null, entry.getValue(), openAPI),
                        sourceDirPath);
                }
            }
        }
    }

    private record OperationItem(Operation operation, String requestMethod, String path) {
        public String getOperationId() {
            return operation.getOperationId();
        }
    }

    private record OutputEntry(CodeBlock outputCodeBlock, Object example) {
    }

    private record PropertiesEntry(CodeBlock propertiesCodeBlock, String bodyContentType) {
    }

    private record RequestBodyPropertiesEntry(CodeBlock requestBodyPropertiesCodeBlock, String bodyContentType) {
    }

    private static class GeneratorConfig {
        @JsonProperty
        private OpenApi openApi = new OpenApi();

        private static class OpenApi {
            @JsonProperty
            private List<String> operations = Collections.emptyList();

            @JsonProperty
            private List<String> oAuth2Scopes = Collections.emptyList();

            @JsonProperty
            private boolean useTags = false;
        }
    }
}
