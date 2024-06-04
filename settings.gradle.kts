rootProject.name = "twtool-admin"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        mavenCentral()
    }
}

include(":admin-bootstrap")
include(":admin-common")
include(":admin-framework")

include(":admin-system")