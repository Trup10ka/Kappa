plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.trup10ka.kappa"
version = "1.0.0"

repositories {
    mavenCentral()
}

/* ==== HikariCP ==== */
var hikariCPVersion = "6.2.1"

/* ==== Config ==== */
var hoconParserVersion = "3.8.1"

/* ====  Utils  ==== */
var jetbrainsAnnotationsVersion = "24.0.0"
var jdbcVersion = "8.0.33"
var slf4jVersion = "2.0.9"

/* ==== JUnit 5 ==== */
var junitVersion = "5.10.0"

dependencies {

    /* ==== HikariCP ==== */
    implementation("com.zaxxer:HikariCP:$hikariCPVersion")

    /* ==== Config ==== */
    implementation("com.electronwill.night-config:hocon:$hoconParserVersion")

    /* ====  Utils  ==== */
    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    implementation("mysql:mysql-connector-java:$jdbcVersion")
    implementation("org.slf4j:slf4j-nop:$slf4jVersion")

    /* ==== JUnit 5 ==== */
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.trup10ka.kappa.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.trup10ka.kappa.Main"
    }
}

tasks.shadowJar {
    manifest {
        attributes["Description"] = "Console client app for Kappa"
        archiveBaseName = "Kappa"
        archiveClassifier = ""
    }
}

tasks.test {
    useJUnitPlatform()
}
