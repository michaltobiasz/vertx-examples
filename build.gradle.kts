import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.10"
    kotlin("kapt") version "1.3.31"
}

group = "vertx.examples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.slf4j:slf4j-jdk14:1.7.26")
    compile("io.vertx:vertx-core:3.7.0")
    compile("io.vertx:vertx-web-client:3.7.0")
    compile("io.vertx:vertx-web:3.7.0")
    compile("io.vertx:vertx-web-templ-handlebars:3.7.0")

    compile("io.vertx:vertx-service-proxy:3.7.0")
    kapt("io.vertx:vertx-codegen:3.7.0:processor")
    compileOnly("io.vertx:vertx-codegen:3.7.0")

    compile("io.vertx:vertx-rx-java2:3.7.0")
    compile("io.vertx:vertx-rx-java2-gen:3.7.0") // needed for Proxy Services to work with rx java
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}