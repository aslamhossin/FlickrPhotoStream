plugins {
    // Android Library Plugin
    alias(libs.plugins.android.library)

    // Kotlin Plugins
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)

    // Hilt Plugin
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
android {
    namespace = "me.aslamhossin.data"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }
    flavorDimensions += "default"

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"https://www.flickr.com/services/feeds/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"https://www.flickr.com/services/feeds/\"")
        }
        create("stage") {
            buildConfigField("String", "BASE_URL", "\"https://www.flickr.com/services/feeds/\"")
        }
        create("prod") {
            buildConfigField("String", "BASE_URL", "\"https://www.flickr.com/services/feeds/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }



}

dependencies {
    // Project Modules
    implementation(project(":core"))
    implementation(project(":domain"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Utilities
    implementation(libs.android.joda)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Test Dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)

}
