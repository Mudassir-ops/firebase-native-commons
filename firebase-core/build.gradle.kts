plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.plugins)
    id("kotlin-kapt")
}
fun getExtDefault(key: String, default: String = ""): String =
    (rootProject.extra[key] as? String) ?: default

android {
    namespace = "com.example.firebase_core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
        singleVariant("debug") {
            withSourcesJar()
            withJavadocJar()
        }
    }

}
kapt {
    correctErrorTypes = true
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // implementation(libs.play.services.measurement.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //dagger Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))

    // Add the dependency for the Analytics library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-analytics")

    val useLocalModule =
        project.findProperty("useLocalModules")?.toString()?.toBoolean() ?: true
    if (useLocalModule) {
        // implementation(project.android)
    } else {
        extra.set("GROUP_ID", "firebase-native-commons")



//        debugImplementation(
//            "${getExtDefault("GROUP_ID")}:${getExtDefault("FIREBASE_EVENT_ARTIFACT_ID")}-debug:${
//                getExtDefault(
//                    "FIREBASE_EVENT_DEBUG_VERSION"
//                )
//            }"
//        )
//        releaseImplementation(
//            "${getExtDefault("GROUP_ID")}:${getExtDefault("FIREBASE_EVENT_ARTIFACT_ID")}:${
//                getExtDefault(
//                    "FIREBASE_EVENT_RELEASE_VERSION"
//                )
//            }"
//        )

    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = getExtDefault("GROUP_ID")
            artifactId = getExtDefault("FIREBASE_EVENT_ARTIFACT_ID")
            version = getExtDefault("FIREBASE_EVENT_RELEASE_VERSION")
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set("FIREBASE_EVENT SDK")
                description.set("A FIREBASE_EVENT SDK for  android App")
                url.set("https://github.com/Mudassir-ops/firebase-native-commons.git")
            }
        }
        create<MavenPublication>("debug") {
            groupId = getExtDefault("GROUP_ID")
            artifactId = getExtDefault("FIREBASE_EVENT_ARTIFACT_ID").plus("-debug")
            version = getExtDefault("FIREBASE_EVENT_DEBUG_VERSION")

            afterEvaluate {
                from(components["debug"])
            }
            pom {
                name.set("FIREBASE_EVENT SDK")
                description.set("A FIREBASE_EVENT SDK for  android App")
                url.set("hhttps://github.com/Mudassir-ops/firebase-native-commons")
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Mudassir-ops/firebase-native-commons")
            credentials {
                username = getExtDefault("githubUsername")
                password = getExtDefault("githubToken")
            }
        }
    }
}