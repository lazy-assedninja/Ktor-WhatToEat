val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val h2DatabaseVersion: String by project
val mysqlDatabaseVersion: String by project
val commonsEmailVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.0"
}

group = "me.lazy_assedninja"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")

    // Server Engine
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Serialization
    implementation ("io.ktor:ktor-serialization:$ktorVersion")

    // Json
    implementation("io.ktor:ktor-gson:$ktorVersion")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    // Database
    implementation("com.h2database:h2:$h2DatabaseVersion")
    implementation("mysql:mysql-connector-java:$mysqlDatabaseVersion")

    // Commons Email
    implementation("org.apache.commons:commons-email:$commonsEmailVersion")

    // Test
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}