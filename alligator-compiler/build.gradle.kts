plugins {
    id("java")
    id("maven-publish")
}

group = "com.github.aartikov"
version = "4.7.0"

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.squareup:javapoet:1.10.0")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
        }
    }
}