buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.classpath.kotlinGradlePlugin)
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.VERSION