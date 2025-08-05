import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.services) apply false
}

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

gradle.rootProject {
    extra.set("minSdk", 24)
    extra.set("compileSdk", 35)
    extra.set("JavaVersion", JavaVersion.VERSION_17)
    extra.set("jvmTarget", "17")

    extra.set("GROUP_ID", "aio.app.common")

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

subprojects {
    apply(plugin = "maven-publish")
}
