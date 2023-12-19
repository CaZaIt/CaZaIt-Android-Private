pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}
rootProject.name = "CaZaIt"
include(
    ":app",
    ":core:model",
    ":core:data",
    ":core:database",
    ":core:datastore",
    ":core:domain"
)
include(":feature:signin")
include(":feature:navigation")
include(":feature:splash")
include(":feature:home")
include(":feature:map")
include(":feature:mypage")
include(":feature:history")
include(":feature:plus")
include(":main")
include(":core:designsystem")
