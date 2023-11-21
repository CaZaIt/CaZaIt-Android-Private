plugins {
    id("cazait.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "org.cazait.core.datastore"

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)
}
