package guru.drako.trainings.kotlin.day2.mavenlite

data class Artifact(
  val id: String,
  val version: String,
  val dependencies: Set<Dependency> = setOf()
)
