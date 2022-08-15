import dev.s7a.gradle.minecraft.server.MinecraftServerConfig
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask

plugins {
    java
    idea
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    // https://github.com/sya-ri/minecraft-server-gradle-plugin
    id("dev.s7a.gradle.minecraft.server") version "1.2.0"
}

group = "engineer.skyouo.plugins"
version = "1.0.0-1.19"

// Define the "bundle" configuration which will be included in the shadow jar.
val bundle: Configuration by configurations.creating

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")?.let { bundle(it) }
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")?.let { bundle(it) }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")?.let { bundle(it) }

    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks {
    processResources {
        val props = mutableMapOf("version" to version)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    jar {
        archiveClassifier.set("source")
        from(rootProject.file("LICENSE"))
    }

    shadowJar {
        archiveClassifier.set("")
        // Include our bundle configuration in the shadow jar.
        configurations = listOf(bundle)
    }

    build {
        dependsOn(shadowJar)
    }

    task<LaunchMinecraftServerTask>("buildAndLaunchServer") {
        dependsOn(build)
        doFirst {
            copy {
                from(buildDir.resolve("libs/BirthdayPlugin-$version.jar"))
                into(buildDir.resolve("MinecraftServer/plugins"))
            }
        }

        // Spigot: https://getbukkit.org/download/spigot
        // Paper: https://github.com/sya-ri/minecraft-server-gradle-plugin
        jarUrl.set(LaunchMinecraftServerTask.JarUrl.Paper("1.19.2"))
        agreeEula.set(true)
    }

    task("reloadPluginJar") {
        dependsOn(build)
        doFirst {
            copy {
                from(buildDir.resolve("libs/BirthdayPlugin-$version.jar"))
                into(buildDir.resolve("MinecraftServer/plugins"))
            }
        }
    }
}

tasks.withType(JavaCompile::class.java).configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}