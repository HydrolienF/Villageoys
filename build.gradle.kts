import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

// settings can be edited in gradle.properties.

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.5.11"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    paperweight.foliaDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.TownyAdvanced:Towny:0.99.6.0")
    // compileOnly("io.github.townyadvanced.commentedconfiguration:CommentedConfiguration:1.0.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // compileOnly("com.github.HydrolienF:FlagsH:3.0.3") //TODO later
}

// java {
  // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
//   toolchain.languageVersion.set(JavaLanguageVersion.of(17))
// }

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    shadowJar {
        relocate("org.bstats","${project.group}.bstats")
        relocate("co.aikar.commands","${project.group}.acf")
        // archiveFileName.set("${project.name}-${project.version}.jar")
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17) // See https://openjdk.java.net/jeps/247 for more information.
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
        val props = mapOf(
            "name" to project.name,
            "version" to project.version,
            "description" to project.description,
            "apiVersion" to project.extra["mcApiVersion"],
            "group" to project.group
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}