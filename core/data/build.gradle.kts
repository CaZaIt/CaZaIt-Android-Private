plugins {
    id("cazait.android.library")
    id("cazait.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "org.cazait.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.core.database)

    // 네트워크
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
}
