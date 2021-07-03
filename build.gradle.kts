import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.min"
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

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(group = "net.dv8tion", name = "JDA", version = "4.3.0_277")
    implementation(group = "com.googlecode.json-simple", name = "json-simple", version = "1.1.1")
    implementation(group = "org.slf4j", name = "slf4j-log4j12", version = "1.7.31")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.6.0")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}