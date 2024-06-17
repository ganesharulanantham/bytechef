dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    implementation(libs.commons.validator)
    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")
    implementation(project(":server:libs:platform:platform-security:platform-security-web-api"))
    implementation(project(":server:libs:platform:platform-user:platform-user-api"))

    testImplementation("org.apache.commons:commons-lang3")
    testImplementation("org.springframework:spring-context-support")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(project(":server:libs:config:jdbc-config"))
    testImplementation(project(":server:libs:config:liquibase-config"))
    testImplementation(project(":server:libs:platform:platform-user:platform-user-service"))
    testImplementation(project(":server:libs:test:test-int-support"))
}
