import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.spring") version "1.4.20"
    jacoco
}

group = "au.com.tony.sample.webflux"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.logstash.logback:logstash-logback-encoder:6.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")

    testImplementation("org.mock-server:mockserver-junit-jupiter:5.11.1")
    implementation(kotlin("stdlib-jdk8"))
}

sourceSets {
    val main by getting
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks {
    bootJar {
        launchScript()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport", "jacocoTestCoverageVerification")
}

val jacocoExclude = sourceSets.main.get().output.asFileTree.matching {
    "au/com/tony"
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
        classDirectories.setFrom(jacocoExclude)
    }
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = "0.90".toBigDecimal()
                }
            }
        }
        classDirectories.setFrom(jacocoExclude)
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
