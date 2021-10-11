plugins {
    kotlin("jvm")
    `java-library`
    id("com.vanniktech.maven.publish")
}

dependencies {
    api(project(":geojson"))
    implementation(libs.moshi)

    testImplementation(libs.jsonAssert)
    testImplementation(libs.kotlinTestJunit)
}
