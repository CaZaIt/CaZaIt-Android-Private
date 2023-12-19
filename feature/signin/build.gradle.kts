plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.signin"
}

dependencies {
    implementation(projects.core.domain)
}
