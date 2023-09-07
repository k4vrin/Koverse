import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.9"
}