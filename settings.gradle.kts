pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "geojson-kotlin"
include("geojson")
include("geojson-moshi")

enableFeaturePreview("VERSION_CATALOGS")
