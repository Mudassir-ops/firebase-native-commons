plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.google.ksp) apply false
    id("com.google.firebase.crashlytics") version "3.0.5" apply false

}
subprojects {
    apply(plugin = "maven-publish")
}
