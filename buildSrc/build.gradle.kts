plugins {
    kotlin("jvm") version "1.6.20"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.1.3")
//    implementation("com.android.tools.build:gradle:7.1.3")
//    api(gradleKotlinDsl())
//    api(kotlin("gradle-plugin", version = "1.6.20"))
//    api(kotlin("gradle-plugin-api", version = "1.6.20"))
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("org.ow2.asm:asm-util:7.0")
}