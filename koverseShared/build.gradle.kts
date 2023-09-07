@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    with(libs.plugins) {
        id(multiplatform.get().pluginId)
        id(cocoapods.get().pluginId)
        id(androidLib.get().pluginId)
        alias(kotlinXSerialization)
        alias(sqlDelight)
        alias(kmpNativeCoroutine)
        alias(ksp)
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.VERSION

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    jvm()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.majorVersion
            }
        }

    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()


    cocoapods {
        summary = "Koverse shared module"
        homepage = "kavrin.dev"
        version = "1.0"
        ios.deploymentTarget = "15"
        podfile = project.file("../koverseIosApp/Podfile")
        framework {
            baseName = "koverseShared"
            isStatic = false
        }
    }



    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        val commonMain by getting {
            dependencies {
                // Koin
                implementation(libs.koin.core)

                // Serialization
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)

                // Coroutine
                implementation(libs.kotlinx.coroutine.core)

                // Kotlinx DateTime
                implementation(libs.kotlinx.dateTime)

                // Ktor
                implementation(libs.bundles.ktor.shared)

                // Kermit
                implementation(libs.kermit)

                // Multiplatform settings
                implementation(libs.mpsettings.core)
                implementation(libs.mpsettings.coroutine)

                // SqlDelight
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutine)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.mpsettings.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.turbine.core)
                implementation(libs.koin.test)
                implementation(libs.kotest.assertion)
                implementation(libs.mockative.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.androidDriver)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.androidx.dataStore)
                implementation(libs.mpsettings.dataStore)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.sqldelight.jvmDriver)
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.rules)
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit.ktx)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                dependsOn(androidInstrumentedTest)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.kmongo)
                implementation(libs.kmongo.coroutine)
                implementation(libs.ktor.client.java)
                implementation(libs.sqldelight.jvmDriver)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.nativeDriver)
                implementation(libs.ktor.client.ios)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

}

android {
    namespace = GeneralConfig.PROJECT_ID
    compileSdk = AndroidConfig.CompileSDK
    defaultConfig {
        minSdk = AndroidConfig.MinSDK
        targetSdk = AndroidConfig.CompileSDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

sqldelight {
    databases {
        create("koverse-db") {
            packageName.set("dev.kavrin.koverse.database")
        }
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, libs.mockative.processor)
        }
}