plugins {
    id("java")
    id("application")
}

group = "com.trup10ka.kappa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

var jetbrainsAnnotationsVersion = "24.0.0"

dependencies {

    /* ====  Utils  ==== */
    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.trup10ka.kappa.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.trup10ka.kappa.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}
