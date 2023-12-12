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
        implementation(libs, "androidx.core.ktx")
        implementation(libs, "androidx.appcompat")
        implementation(libs, "androidx.constraintlayout")
        implementation(libs, "androidx.navigation.fragment.ktx")
        implementation(libs, "androidx.navigation.ui.ktx")
        implementation(libs, "androidx.fragment.ktx")
        implementation(libs, "androidx.lifecycle.viewmodel.ktx")
        implementation(libs, "androidx.core.splashscreen")

        testImplementation(libs, "junit")
        androidTestImplementation(libs, "androidx.test.ext.junit")
        androidTestImplementation(libs, "androidx.test.espresso.core")
    }
}
