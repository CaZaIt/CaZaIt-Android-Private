plugins {
    id("cazait.android.feature")
}

android {
    namespace = "org.cazait.feature.mypage"
}

dependencies {
    implementation(projects.core.domain)
}
