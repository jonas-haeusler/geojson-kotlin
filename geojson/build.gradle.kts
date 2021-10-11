plugins {
    kotlin("jvm")
    `java-library`
    id("com.vanniktech.maven.publish")
}

dependencies {
    testImplementation(libs.kotlinTestJunit)
}
