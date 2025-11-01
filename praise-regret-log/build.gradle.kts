plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.12.0"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
}

group = "online.rabko"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springdoc", name = "springdoc-openapi-starter-webmvc-ui", version = "2.6.0")

    implementation(group = "org.postgresql", name = "postgresql", version = "42.7.4")
    implementation(group = "org.liquibase", name = "liquibase-core")

    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-security")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jdbc")
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }

    val oasResourcesDir = "$projectDir/src/main/resources/static/oas"
    val buildDir = layout.buildDirectory.get()
    openApiGenerate {
        generatorName.set("kotlin-spring")
        inputSpec.set("$oasResourcesDir/log.yaml")
        outputDir.set("$buildDir/generated")
        apiPackage.set("online.rabko.api")
        modelPackage.set("online.rabko.model")
        library.set("spring-boot")
        configOptions.set(
            mapOf(
                "useSpringBoot3" to "true",
                "useSwaggerUI" to "true",
                "interfaceOnly" to "true",
                "skipDefaultInterface" to "true",
                "openApiNullable" to "false",
                "useTags" to "true"
            )
        )
    }
}


sourceSets {
    main {
        kotlin.srcDir("$buildDir/generated/src/main/kotlin")
    }
}
