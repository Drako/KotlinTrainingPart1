package guru.drako.trainings.kotlin.day2.mavenlite

class ArtifactNotFoundException(val artifact: String)
  : RuntimeException("$artifact could not be resolved on any of the repositories.")
