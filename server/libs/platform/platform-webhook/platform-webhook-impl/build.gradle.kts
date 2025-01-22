dependencies {
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework:spring-context")
    implementation(project(":server:libs:atlas:atlas-file-storage:atlas-file-storage-api"))
    implementation(project(":server:libs:atlas:atlas-worker:atlas-worker-api"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:core:message:message-broker:message-broker-sync"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-coordinator:platform-workflow-coordinator-api"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-execution:platform-workflow-execution-api"))
    implementation(project(":server:libs:platform:platform-file-storage:platform-file-storage-api"))
    implementation(project(":server:libs:platform:platform-coordinator"))
    implementation(project(":server:libs:platform:platform-webhook:platform-webhook-api"))
    implementation(project(":server:libs:modules:components:map"))
    implementation(project(":server:libs:modules:task-dispatchers:branch"))
    implementation(project(":server:libs:modules:task-dispatchers:condition"))
    implementation(project(":server:libs:modules:task-dispatchers:each"))
    implementation(project(":server:libs:modules:task-dispatchers:fork-join"))
    implementation(project(":server:libs:modules:task-dispatchers:loop"))
    implementation(project(":server:libs:modules:task-dispatchers:map"))
    implementation(project(":server:libs:modules:task-dispatchers:parallel"))
    implementation(project(":server:libs:modules:task-dispatchers:subflow"))
}
