
@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
}

fun getAuthentProperties(file: String = "apikey.properties"): HashMap<String, *> {
    val apikeyPropertiesFile = rootProject.file(file)
    val items = HashMap<String, String>()

    if (apikeyPropertiesFile.exists()) {
        apikeyPropertiesFile.forEachLine {
            items[it.split("=")[0]] = it.split("=")[1]
        }
    } else {
        logger.error("make sure to have apikey.properties in your root folder")
    }

    return items
}

android {
    namespace = "com.chaliez.alma.test"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.chaliez.alma.test"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.chaliez.alma.test.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val authentProperties = getAuthentProperties()
        buildConfigField("String", "APIPublic", authentProperties["API_KEY"] as String)
        buildConfigField("String", "APIPrivate", authentProperties["API_SECRET"] as String)
        buildConfigField("String", "APITS", authentProperties["API_TS"] as String)

        // Enable room auto-migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
        aidl = false
        renderScript = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Arch Components
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.accompanist.coil)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.animation)
//    implementation(libs.accompanist.systemcontroller)

    // Network
    implementation(libs.network.retrofit)
    implementation(libs.network.retrofit.moshi)
    implementation(libs.network.retrofit.logging)

    // JSON
    implementation(libs.moshi)

    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Instrumented tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests: jUnit rules and runners
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)

    // Test Navigation
    androidTestImplementation(libs.androidx.navigation.test)
}
