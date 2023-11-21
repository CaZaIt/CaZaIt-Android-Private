package org.cazait

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureViewAndroid() {
    val libs = extensions.libs

    with(pluginManager) {
        apply("androidx.navigation.safeargs.kotlin")
    }

    androidExtension.apply {
        buildFeatures {
            dataBinding.enable = true
        }
    }

    dependencies {
        implementation(libs, "androidx.core")
        implementation(libs, "androidx.appcompat")
        implementation(libs, "androidx.constraintlayout")
        implementation(libs, "androidx.navigation.fragment")
        implementation(libs, "androidx.navigation.ui")
        implementation(libs, "androidx.recyclerview")
        implementation(libs, "androidx.fragment")
        implementation(libs, "androidx.activity")
        implementation(libs, "androidx.lifecycle.livedata")
        implementation(libs, "androidx.lifecycle.runtime")
        implementation(libs, "androidx.lifecycle.viewmodel")
        implementation(libs, "androidx.work")
        implementation(libs, "androidx.splash")

        testImplementation(libs, "junit4")
        androidTestImplementation(libs, "androidx.test.ext")
        androidTestImplementation(libs, "androidx.test.espresso.core")
    }
}
