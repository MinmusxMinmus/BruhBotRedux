import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "com.minmusxminmus"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "m2-dv8tion"
        url = uri("https://m2.dv8tion.net/releases")
    }
}

dependencies {
    implementation(fileTree("lib"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(group = "net.dv8tion", name = "JDA", version = "4.3.0_277")
    implementation(group = "org.apache.logging.log4j", name = "log4j-slf4j-impl", version = "2.14.1")
    implementation(group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-yaml", version = "2.13.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}