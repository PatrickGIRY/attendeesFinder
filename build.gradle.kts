plugins {
    java
}

group = "functional.programming"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.1.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
