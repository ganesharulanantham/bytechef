group = "com.bytechef.configuration"
description = ""

springBoot {
    mainClass.set("com.bytechef.configuration.ConfigurationApplication")
}

dependencies {
    implementation("org.apache.commons:commons-lang3")
    implementation(libs.org.openapitools.jackson.databind.nullable)
    implementation(libs.org.springdoc.springdoc.openapi.starter.common)
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.retry:spring-retry")
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-config"))
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-converter"))
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-repository:atlas-configuration-repository-jdbc"))
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-service"))
    implementation(project(":server:libs:atlas:atlas-file-storage:atlas-file-storage-service"))
    implementation(project(":server:libs:automation:automation-configuration:automation-configuration-rest:automation-configuration-rest-impl"))
    implementation(project(":server:libs:automation:automation-configuration:automation-configuration-service"))
    implementation(project(":server:libs:automation:automation-swagger"))
    implementation(project(":server:libs:config:async-config"))
    implementation(project(":server:libs:config:automation-demo-config"))
    implementation(project(":server:libs:config:cache-config"))
    implementation(project(":server:libs:config:environment-config"))
    implementation(project(":server:libs:config:jackson-config"))
    implementation(project(":server:libs:config:jdbc-config"))
    implementation(project(":server:libs:config:liquibase-config"))
    implementation(project(":server:libs:config:logback-config"))
    implementation(project(":server:libs:config:rest-config"))
    implementation(project(":server:libs:core:commons:commons-data"))
    implementation(project(":server:libs:embedded:embedded-configuration:embedded-configuration-instance-impl"))
    implementation(project(":server:libs:embedded:embedded-configuration:embedded-configuration-public-rest:embedded-configuration-public-rest-impl"))
    implementation(project(":server:libs:embedded:embedded-configuration:embedded-configuration-rest:embedded-configuration-rest-impl"))
    implementation(project(":server:libs:embedded:embedded-configuration:embedded-configuration-service"))
    implementation(project(":server:libs:embedded:embedded-configuration:embedded-configuration-user-token-rest"))
    implementation(project(":server:libs:embedded:embedded-swagger"))
    implementation(project(":server:libs:platform:platform-category:platform-category-service"))
    implementation(project(":server:libs:platform:platform-component:platform-component-rest"))
    implementation(project(":server:libs:platform:platform-configuration:platform-configuration-rest:platform-configuration-rest-impl"))
    implementation(project(":server:libs:platform:platform-configuration:platform-configuration-service"))
    implementation(project(":server:libs:platform:platform-oauth2:platform-oauth2-service"))
    implementation(project(":server:libs:platform:platform-rest:platform-rest-impl"))
    implementation(project(":server:libs:platform:platform-swagger"))
    implementation(project(":server:libs:platform:platform-tag:platform-tag-service"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-test:platform-workflow-test-rest"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-test:platform-workflow-test-service"))

    implementation(project(":server:libs:atlas:atlas-worker:atlas-worker-api"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-worker:platform-workflow-worker-api"))

    implementation(project(":server:ee:libs:atlas:atlas-configuration:atlas-configuration-repository:atlas-configuration-repository-git"))
    implementation(project(":server:ee:libs:atlas:atlas-execution:atlas-execution-remote-client"))
    implementation(project(":server:ee:libs:atlas:atlas-worker:atlas-worker-remote-config"))
    implementation(project(":server:ee:libs:automation:automation-configuration:automation-configuration-remote-rest"))
    implementation(project(":server:ee:libs:core:discovery:discovery-redis"))
    implementation(project(":server:ee:libs:core:commons:commons-rest-controller"))
    implementation(project(":server:ee:libs:config:tenant-multi-data-config"))
    implementation(project(":server:ee:libs:embedded:embedded-configuration:embedded-configuration-remote-rest"))
    implementation(project(":server:ee:libs:platform:platform-component:platform-component-registry:platform-component-registry-remote-client"))
    implementation(project(":server:ee:libs:platform:platform-configuration:platform-configuration-remote-rest"))
    implementation(project(":server:ee:libs:platform:platform-connection:platform-connection-remote-client"))
    implementation(project(":server:ee:libs:platform:platform-user:platform-user-remote-client"))
    implementation(project(":server:ee:libs:platform:platform-workflow:platform-workflow-task-dispatcher:platform-workflow-task-dispatcher-registry:platform-workflow-task-dispatcher-registry-remote-client"))
    implementation(project(":server:ee:libs:platform:platform-workflow:platform-workflow-execution:platform-workflow-execution-remote-client"))

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.zaxxer:HikariCP")
    runtimeOnly(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("org.springframework.boot:spring-boot-starter-aop")
    runtimeOnly("org.springframework.boot:spring-boot-starter-cache")

    testImplementation(project(":server:libs:test:test-int-support"))
}
