plugins {
    kotlin("jvm") version "2.0.21"
    alias(libs.plugins.kotlin.serialization)
    `java-library`
    signing
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "co.hashline"
version = "0.9.0"

repositories {
    mavenCentral()
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
    jvmToolchain(21)
}

// Maven Central publishing configuration
mavenPublishing {
    // Always sign all publications
    signAllPublications()

    coordinates("co.hashline", "components-kt", version.toString())

    // Enable Maven Central publishing
    publishToMavenCentral()

    pom {
        name = "Components-KT"
        description = "A lightweight Kotlin library for building Server driven UI components."
        inceptionYear = "2025"
        url = "https://github.com/hash-line/components-kt"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "HashimAli09"
                name = "Hashim Ali"
                url = "https://github.com/HashimAli09"
            }
        }
        scm {
            connection.set("scm:git:git://github.com/hash-line/components-kt.git")
            developerConnection.set("scm:git:ssh://github.com:hash-line/components-kt.git")
            url.set("https://github.com/hash-line/components-kt")
        }
    }
}