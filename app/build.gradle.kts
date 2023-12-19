plugins {
    id("cazait.android.application")
    id("cazait.android.view")
    id("cazait.android.compose")
}

android {
    namespace = "org.cazait"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.cazait"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // okHttp
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    // lifecycle
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.car.ui.lib)

    // room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)
    kapt(libs.androidx.room.compiler)

    // viewpager2
    implementation(libs.androidx.viewpager2)

    /* Third Party Library */

    // expandable layout
    implementation(libs.expandablelayout)

    // naver maps
    implementation(libs.map.sdk)

    // location request
    implementation(libs.play.services.location)

    // swipe refresh
    implementation(libs.androidx.swiperefreshlayout)

    // Dot Indicator
    implementation(libs.dotsindicator)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.material)
    // coil
    implementation(libs.coil)

    // paging3
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    // Dot Indicator
    implementation(libs.dotsindicator)

    // 네이버 지도 SDK
    implementation(libs.map.sdk)
    implementation(libs.play.services.location)

    // EasyPermissions
    implementation(libs.easypermissions)

    // Lottie Animation
    implementation(libs.lottie)
}
