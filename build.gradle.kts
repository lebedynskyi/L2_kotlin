import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.4.20"
}

allprojects {
    group = "me.user"
    version = "1.0-SNAPSHOT"

    repositories {
        jcenter()
        mavenCentral()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        implementation(it)
    }
    implementation ("com.sksamuel.hoplite:hoplite-core:1.3.12")
    implementation ("com.sksamuel.hoplite:hoplite-yaml:1.3.12")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}