plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.map"
}

dependencies {
    implementation(projects.core.domain)
}
