plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

val queryDslVersion: String by extra

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.18.0")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // Database
    runtimeOnly("com.mysql:mysql-connector-j:8.4.0")
    implementation("com.zaxxer:HikariCP")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")

    // kafka
    implementation("org.springframework.kafka:spring-kafka:3.3.5")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Mail
    implementation("org.springframework.boot:spring-boot-starter-mail")
}

val generated = file("src/main/generated")
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory = generated
}

sourceSets {
    main {
        kotlin.srcDirs += file("${projectDir}/build/generated")
    }
}

tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
