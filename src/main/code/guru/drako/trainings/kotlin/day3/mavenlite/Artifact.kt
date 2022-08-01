package guru.drako.trainings.kotlin.day3.mavenlite

data class Artifact(
  val id: String,
  val version: String,
  val dependencies: Set<Dependency> = setOf()
)
