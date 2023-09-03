@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(libs.plugins) {
        id(multiplatform.get().pluginId)
        id(androidLib.get().pluginId)
//        id(cocoapods.get().pluginId)
        alias(kotlinXSerialization)
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.VERSION


kotlin {
    jvmToolchain(17)
    androidTarget()
//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()
    jvm()



    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutine.core)
            }
        }
        val commonTest by getting {
            dependencies {  }
        }
//        val iosMain by getting {
//            dependencies {  }
//        }
//        val iosTest by getting {
//            dependencies {  }
//        }
        val androidMain by getting {
            dependencies {  }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.kmongo)
                implementation(libs.kmongo.coroutine)
            }
        }
    }
}

android {
    namespace = GeneralConfig.PROJECT_ID

}