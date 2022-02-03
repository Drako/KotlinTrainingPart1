import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.6.10"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
  id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
  id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
}

group = "guru.drako.trainings.kotlin"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

object Version {
  const val JUNIT = "5.8.2"
  const val KOTEST = "5.1.0"
  const val KOTLINX_COROUTINES = "1.6.0"
  const val KOTLINX_SERIALIZATION = "1.3.2"
  const val LOGBACK = "1.2.10"
  const val MOCKK = "1.12.2"
  const val SLF4J = "1.7.33"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  testImplementation(kotlin("test-junit5"))

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.KOTLINX_COROUTINES}")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Version.KOTLINX_COROUTINES}")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${Version.KOTLINX_COROUTINES}")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.KOTLINX_SERIALIZATION}")

  implementation("org.slf4j:slf4j-api:${Version.SLF4J}")
  runtimeOnly("ch.qos.logback:logback-classic:${Version.LOGBACK}")

  testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.JUNIT}")
  testImplementation("org.junit.jupiter:junit-jupiter-params:${Version.JUNIT}")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.JUNIT}")

  testImplementation("io.mockk:mockk:${Version.MOCKK}")

  testImplementation("io.kotest:kotest-assertions-core-jvm:${Version.KOTEST}")
  testImplementation("io.kotest:kotest-property-jvm:${Version.KOTEST}")
}

kotlin.sourceSets {
  main {
    kotlin.srcDirs("src/main/code")
  }

  test {
    kotlin.srcDirs("src/test/code")
  }
}

sourceSets {
  main {
    java.srcDirs("src/main/code")
  }

  test {
    java.srcDirs("src/test/code")
  }
}

allOpen {
  annotation("guru.drako.trainings.kotlin.day3.Open")
}

noArg {
  annotation("guru.drako.trainings.kotlin.day3.NoArg")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks {
  withType<Test> {
    // important for JUnit5
    useJUnitPlatform()
  }

  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }

  "wrapper"(Wrapper::class) {
    gradleVersion = "7.2"
  }
}
