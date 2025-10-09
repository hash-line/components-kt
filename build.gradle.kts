plugins {
    kotlin("jvm") version "2.0.21"
    alias(libs.plugins.kotlin.serialization)
}

group = "co.hashline"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/hash-line/events-kt")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation(libs.jetbrains.kotlin.stdlib)

    implementation(libs.hashline.events)

    // Coroutines for using Kotlin Flows
    implementation(libs.jetbrains.kotlinx.coroutines)
    implementation(libs.jetbrains.kotlinx.serialization)

    // Add kotlinx-datetime dependency
    implementation(libs.jetbrains.kotlinx.datetime)


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}