pluginManagement{
    repositories.gradlePluginPortal()
    includeBuild("gradle/plugins")

}
dependencyResolutionManagement{
    repositories.mavenCentral()
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}
rootProject.name = "sec-helper"
include("app")
include("sec")
