plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.0.3")
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("org.ow2.asm:asm-util:7.0")
}