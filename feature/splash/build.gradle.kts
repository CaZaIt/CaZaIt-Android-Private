plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.splash"
}

dependencies {
    implementation(projects.core.domain)
}
