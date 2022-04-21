rootProject.name = "delivery-service"

pluginManagement {
    val properties = java.util.Properties()
    properties.load(File(rootDir, "gradle.properties").inputStream())

    val springBootVersion: String by properties
    val springDependencyManagement: String by properties
    val springPlugin: String by properties
    val kotlinJvmVersion: String by properties

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagement
        kotlin("plugin.spring") version springPlugin
        kotlin("jvm") version kotlinJvmVersion
    }
}
