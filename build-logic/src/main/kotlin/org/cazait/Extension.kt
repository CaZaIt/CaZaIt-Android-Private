package org.cazait

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

internal val Project.applicationExtension: CommonExtension<*, *, *, *, *>
    get() = extensions.getByType<ApplicationExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension: CommonExtension<*, *, *, *, *>
    get() = runCatching { libraryExtension }
        .recoverCatching { applicationExtension }
        .onFailure { println("Could not find Library or Application extension from this project") }
        .getOrThrow()

internal val ExtensionContainer.libs: VersionCatalog
    get() = getByType<VersionCatalogsExtension>().named("libs")

internal fun DependencyHandlerScope.implementation(libs: VersionCatalog, library: String) {
    "implementation"(libs.findLibrary(library).get())
}

internal fun DependencyHandlerScope.androidTestImplementation(
    libs: VersionCatalog,
    library: String,
) {
    "androidTestImplementation"(libs.findLibrary(library).get())
}

internal fun DependencyHandlerScope.testImplementation(libs: VersionCatalog, library: String) {
    "testImplementation"(libs.findLibrary(library).get())
}