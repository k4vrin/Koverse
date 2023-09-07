@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(libs.plugins) {
        application
        id(jvm.get().pluginId)
        alias(ktor)
        alias(kotlinXSerialization)
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.getVersion(AppType.Server)

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
dependencies {
    implementation(project(":koverseShared"))
    implementation(libs.bundles.ktor.server)
    implementation(libs.logback)

    implementation(libs.bundles.koin.server)

    implementation(libs.kmongo)
    implementation(libs.kmongo.coroutine)

    testImplementation(libs.bundles.backend.test.jvm)
}
