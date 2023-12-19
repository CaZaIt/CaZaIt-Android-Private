plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.plus"
}

dependencies {
    implementation(projects.core.domain)
}
