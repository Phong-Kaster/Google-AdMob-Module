import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}


android {
    namespace = "com.example.googleadmodmodule"
    compileSdk = 33

    dataBinding {
        android.buildFeatures.dataBinding = true
    }

    defaultConfig {
        applicationId = "com.example.googleadmodmodule"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val input = Date()
        val dateFormat = SimpleDateFormat("MMM-dd-yyyy")
        val output = dateFormat.format(input)
        setProperty ("archivesBaseName", "GoogleAdMobModule-v$versionCode($versionName)-$output")
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



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}



dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    /*1. Google AdMob SDK*/
    implementation("com.google.android.gms:play-services-ads:22.3.0")


    /*2. Navigation Component*/
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")


    /*3. Animation*/
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")


    /*4. Lifecycle observer - Listen for app foregrounding events*/
    val lifecycle_version = "2.3.1"
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    kapt ("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")


    /*5. Shared Preference*/
    implementation("androidx.preference:preference-ktx:1.2.1")


    /*6. Hilt*/
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    // Hilt Activity/ Fragment
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.activity:activity-ktx:1.4.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}