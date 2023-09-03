buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        with(libs.classpath) {
            classpath(kotlinGradlePlugin)
            classpath(androidGradlePlugin)
        }
    }
}

group = GeneralConfig.PROJECT_ID
version = GeneralConfig.VERSION