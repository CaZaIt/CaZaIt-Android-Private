plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.main"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.navigation)
}
