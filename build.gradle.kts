plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id ("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "test")
        property("sonar.projectName", "Capstone")
        property("sonar.qualitygate.wait", true)
        property("sonar.gradle.skipCompile", true)
    }
}