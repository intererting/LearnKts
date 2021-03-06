plugins {
    id("com.android.application")
    id("kotlin-android")
}

//apply<start.MyPlugin>()
//apply<ExamplePlugins>()

//apply<WorkExamplePlugin>()
//apply<ProviderPlugin>()
//apply<ConsumerPlugin>()


apply<dsl.ToyPlugin>()

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.yly.learnkts"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            the<dsl.ToyExtension>().content = "hello world"
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            the<dsl.ToyExtension>().content = "hello world"
            isMinifyEnabled = false
            //传参
//            the<BuildTypeExtension>().invocationParameters = "-debug -log"
        }

    }
    kotlinOptions {
        jvmTarget = "11"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}