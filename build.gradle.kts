val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	kotlin("jvm") version "1.9.23"

	id("io.ktor.plugin") version "2.3.10"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "pe.edu.utec"
version = "0.0.1"

application {
	mainClass.set("pe.edu.utec.ApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("mysql:mysql-connector-java:8.0.22")
	implementation("org.jetbrains.exposed:exposed-core:0.36.2")
	implementation("org.jetbrains.exposed:exposed-dao:0.36.2")
	implementation("org.jetbrains.exposed:exposed-jdbc:0.36.2")

	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-content-negotiation-jvm")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("ch.qos.logback:logback-classic:$logback_version")

	testImplementation("io.ktor:ktor-server-tests-jvm")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
