plugins {
    kotlin("jvm") version "2.0.21"
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
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
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
    }
}

// Publishing configuration
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            
            groupId = "co.hashline"
            artifactId = "components-kt"
            version = "1.0"
            
            pom {
                name.set("Components-KT")
                description.set("A comprehensive Kotlin library for building reusable UI components")
                url.set("https://github.com/hash-line/components-kt")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("hashline")
                        name.set("Hashline")
                        email.set("contact@hashline.co")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/hash-line/components-kt.git")
                    developerConnection.set("scm:git:ssh://github.com:hash-line/components-kt.git")
                    url.set("https://github.com/hash-line/components-kt")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hash-line/components-kt")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}