@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(libs.plugins) {
        id(jvm.get().pluginId)
        alias(ktor)
        alias(kotlinXSerialization)
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.VERSION

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.logback)
    testImplementation(libs.bundles.backend.test)
}
