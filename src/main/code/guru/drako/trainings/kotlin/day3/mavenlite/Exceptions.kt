package guru.drako.trainings.kotlin.day3.mavenlite

class ArtifactNotFoundException(val artifact: String)
  : RuntimeException("$artifact could not be resolved on any of the repositories.")
