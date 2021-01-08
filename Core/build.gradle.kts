import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation ("com.sksamuel.hoplite:hoplite-core:1.3.12")
    implementation ("com.sksamuel.hoplite:hoplite-yaml:1.3.12")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
