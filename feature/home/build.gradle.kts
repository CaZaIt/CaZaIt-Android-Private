plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.home"
}

dependencies {
    implementation(projects.core.domain)
}
