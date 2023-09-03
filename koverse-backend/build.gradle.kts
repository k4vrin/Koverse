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
    implementation(project(":koverse-shared"))
    implementation(libs.bundles.ktor.server)
    implementation(libs.logback)

    implementation(libs.bundles.koin)

    implementation(libs.kmongo)
    implementation(libs.kmongo.coroutine)

    testImplementation(libs.bundles.backend.test.jvm)
}
