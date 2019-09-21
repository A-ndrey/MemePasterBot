import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "dev.andrey"
version = "1.0-SNAPSHOT"

val remoteGroup = "remote"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("rocks.waffle.telekt:telekt:0.6.7")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ApplicationKt"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.register<Upload>("upload") {
    group = remoteGroup
    dependsOn("jar")
}

abstract class RemoteTask : DefaultTask() {
    val host: String by project
    val remoteDir = "${project.name}-${project.version}"
    val jarFileName = "${project.name}-${project.version}.jar"

    fun ssh(vararg args: String) {
        project.exec {
            commandLine("ssh", host, *args)
        }
    }

    fun scp(vararg files: String) {
        project.exec {
            commandLine("scp", *files, "$host:./$remoteDir/")
        }
    }

    @TaskAction
    abstract fun action()
}

open class Upload : RemoteTask() {
    override fun action() {
        ssh("mkdir", "-p", remoteDir)
        scp("./build/libs/$jarFileName", "production/app.properties", "production/run_app.sh", "production/stop_app.sh")
        ssh("chmod", "u+x", "$remoteDir/run_app.sh", "$remoteDir/stop_app.sh")
    }
}
