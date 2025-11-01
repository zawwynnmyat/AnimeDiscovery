plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.apollographql.apollo").version("4.3.3")
}

android {
    namespace = "com.zawmyat.anime_discovery"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.zawmyat.anime_discovery"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Apollo Client
    implementation("com.apollographql.apollo:apollo-runtime:4.3.3")
    implementation("com.apollographql.apollo:apollo-normalized-cache:4.3.3")
    implementation("com.apollographql.apollo:apollo-http-cache:4.3.3")

    // Image loading
    implementation("io.coil-kt:coil-compose:2.3.0")

    //Pagination
    implementation("androidx.paging:paging-runtime:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    //Preferences Data Store
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //For Navigation
    implementation("androidx.navigation:navigation-compose:2.9.5")

    //Koin Dependency Injection
    implementation("io.insert-koin:koin-android:3.5.6")
    implementation("io.insert-koin:koin-androidx-navigation:3.5.6")
    implementation("io.insert-koin:koin-androidx-compose:3.5.6")
    testImplementation("io.insert-koin:koin-test-junit4:3.5.6")

    //Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    //To Visualize Score and Status Distribution with Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //For Onboarding Screen - Horizontal Pager
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    //To interact with oauth
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    //Animation
    implementation("androidx.compose.animation:animation:1.9.4")

    //Horizontal Pager for On-boarding screen
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    //Lottie Animation
    implementation("com.airbnb.android:lottie-compose:6.0.0")

}

apollo {
    service("service") {
        packageName.set("com.zawmyat.anime_discovery")
        generateFragmentImplementations.set(true)
        mapScalarToKotlinInt("FuzzyDateInt")
        mapScalar("Json", "java.lang.Object")
        mapScalar("CountryCode", "kotlin.String")

        introspection {
            endpointUrl.set("https://graphql.anilist.co")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

