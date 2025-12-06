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
    implementation("com.github.Querz:NBT:$querz_nbt_version")
    val log4j_version: String by project;
    implementation("org.apache.logging.log4j:log4j-core:$log4j_version")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4j_version")
    val slf4j_version: String by project;
    implementation("org.slf4j:slf4j-api:$slf4j_version")
    val jackson_version: String by project;
    implementation("tools.jackson.core:jackson-databind:$jackson_version")
}

tasks.jar {
    manifest {
        val main_path = "${project.group}.${project.name.lowercase()}.${project.name}"
        attributes(
            "Main-Class" to main_path,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}