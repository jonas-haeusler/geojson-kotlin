buildscript {

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = libs.versions.kotlin.get()))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}