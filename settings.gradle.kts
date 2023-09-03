pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.google.com")
        maven("https://repo1.maven.org/maven2")
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven("https://maven.google.com")
        maven("https://repo1.maven.org/maven2")
    }
}


rootProject.name = "Koverse"
include(":koverse-backend")
include("koverse-shared")
