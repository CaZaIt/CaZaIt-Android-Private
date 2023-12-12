plugins {
    id("cazait.android.library")
}

android {
    namespace = "org.cazait.core.domain"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.inject)
    implementation(libs.moshi.kotlin)
}
