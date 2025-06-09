plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation ("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.kafka:spring-kafka:3.3.5")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}
