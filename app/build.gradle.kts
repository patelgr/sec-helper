plugins {
    application
}

group = "app.scircle.cli"
version = "0.0.1"


repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

application {
    mainClass.set("app.scircle.Application")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation(project(":sec"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
