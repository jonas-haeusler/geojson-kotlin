plugins {
    kotlin("jvm")
    `java-library`
    id("com.vanniktech.maven.publish")
}

dependencies {
    api(project(":geojson"))
    api(libs.moshi)

    testImplementation(libs.jsonAssert)
    testImplementation(libs.kotlinTestJunit)
}
