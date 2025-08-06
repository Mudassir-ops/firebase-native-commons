import java.io.FileInputStream
import java.util.Properties

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
val versionPropertiesFile = file("versions.properties")
val versionProperties = Properties().apply {
    if (versionPropertiesFile.exists()) {
        load(FileInputStream(versionPropertiesFile))
    }
}
val githubPropertiesFile = file("github.properties")
val githubProperties = Properties().apply {
    if (githubPropertiesFile.exists()) {
        load(FileInputStream(githubPropertiesFile))
    }
}

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

gradle.rootProject {
    extra.set("minSdk", 24)
    extra.set("compileSdk", 35)
    extra.set("JavaVersion", JavaVersion.VERSION_17)
    extra.set("jvmTarget", "17")

    extra.set("GROUP_ID", "firebase-native-commons")

    // Firebase publishing info
    extra.set("FIREBASE_EVENT_ARTIFACT_ID", "firebase-core")
    extra.set(
        "FIREBASE_EVENT_RELEASE_VERSION",
        versionProperties["FIREBASE_EVENT_RELEASE_VERSION"] as? String ?: "1.0.0"
    )
    extra.set(
        "FIREBASE_EVENT_DEBUG_VERSION",
        versionProperties["FIREBASE_EVENT_DEBUG_VERSION"] as? String ?: "1.0.0-debug"
    )

    //  Optional, required only for GitHub publishing
    extra.set("githubUsername", githubProperties["gpr.usr"] as? String)
    extra.set("githubToken", githubProperties["gpr.token"] as? String)
}
rootProject.name = "Firebase-native-commons"
include(":app")
include(":firebase-core")

