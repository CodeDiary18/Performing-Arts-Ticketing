plugins {
    kotlin("jvm") version "1.9.25"
    id("com.diffplug.spotless") version "6.25.0"

    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.3.5" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

group = "com.cd18"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$project/build/**/*.kt")
            ktlint()
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation(kotlin("test"))
        testImplementation("io.mockk:mockk:1.13.17")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
