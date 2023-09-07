@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage", "EditedTargetSdkVersion")
plugins {
    with(libs.plugins) {
        id(android.get().pluginId)
        id(androidApp.get().pluginId)
        alias(kotlinXSerialization)
        alias(ksp)
    }
}
kotlin.jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}


android {
    namespace = AndroidConfig.AppID
    compileSdk = AndroidConfig.CompileSDK
    defaultConfig {
        applicationId = AndroidConfig.AppID
        minSdk = AndroidConfig.MinSDK
        targetSdk = AndroidConfig.CompileSDK
        versionCode = AndroidConfig.VersionCode
        versionName = GeneralConfig.getVersion(AppType.Android)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("META-INF/INDEX.LIST")
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
    applicationVariants.all {
        addJavaSourceFoldersToModel(File(buildDir, "generated/ksp/$name/kotlin"))
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

dependencies {
    implementation(project(":koverseShared"))

    // Compose
    val composeBOM = platform(libs.compose.bom)
    implementation(composeBOM)
    implementation(libs.bundles.compose)
    androidTestImplementation(composeBOM)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.test.manifest)

    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.viewModel)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.biometric)

    // Koin
    implementation(libs.bundles.koin.android)

    // Coroutine
    implementation(libs.kotlinx.coroutine.core)
    implementation(libs.kotlinx.coroutine.android)

    // Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.core)
    implementation(libs.coil.compose)

    // Compose Destinations
    implementation(libs.composeDest.core.animation)
    ksp(libs.composeDest.ksp)
}