plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.history"
}

dependencies {
    implementation(projects.core.domain)
}
