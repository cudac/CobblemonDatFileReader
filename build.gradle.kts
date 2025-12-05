plugins {
    id("java")
    id("com.gradleup.shadow") version("9.2.2")
}

group = "dev.cudac"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io/") }
}

dependencies {
    val querz_nbt_version: String by project;
    shadow("com.github.Querz:NBT:$querz_nbt_version")
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}