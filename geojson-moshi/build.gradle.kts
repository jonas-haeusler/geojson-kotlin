plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    api(project(":geojson"))
    implementation(libs.moshi)
    implementation(libs.moshiAdapters)
    testImplementation(libs.jsonAssert)

    testImplementation(libs.kotlinTestJunit)
}
