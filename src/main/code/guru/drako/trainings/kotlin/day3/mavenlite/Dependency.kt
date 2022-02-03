package guru.drako.trainings.kotlin.day3.mavenlite

data class Dependency(
  val groupId: String,
  val artifactId: String,
  val version: String
) {
  companion object {
    fun of(string: String) = string
      .split(":")
      .let { parts -> Dependency(parts[0], parts[1], parts[2]) }
  }

  override fun toString() = "$groupId:$artifactId:$version"
}

// val dep = Dependency.of("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
