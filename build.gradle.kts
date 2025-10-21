plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
    sourceSets {
        main {
            kotlin.srcDirs("src/main", "src/model", "src/repository", "src/service", "src/util")
        }
    }
}

application {
    mainClass.set("main.MainKt")
}

// Tambahkan blok ini untuk menjaga agar input terminal tetap terbuka
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}