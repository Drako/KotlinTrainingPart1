package guru.drako.trainings.kotlin.day2.mavenlite

import io.mockk.coEvery

@DslMarker
annotation class ArtifactDsl

@ArtifactDsl
interface ArtifactConfigurator {
  var id: String
  var version: String

  operator fun String.unaryPlus()
}

private class ArtifactBuilder : ArtifactConfigurator {
  override lateinit var id: String
  override lateinit var version: String

  private val dependencies = mutableSetOf<Dependency>()

  override fun String.unaryPlus() {
    dependencies.add(Dependency.of(this))
  }

  fun build() = Artifact(id, version, dependencies)
}

fun artifact(
  artifactId: String? = null,
  version: String? = null,
  configure: ArtifactConfigurator.() -> Unit = {}
): Artifact {
  return ArtifactBuilder()
    .apply {
      artifactId?.let { id = it }
      version?.let { this.version = it }
    }
    .apply(configure).build()
}

fun artifact(
  groupId: String,
  artifactId: String? = null,
  version: String? = null,
  repository: Repository,
  configure: ArtifactConfigurator.() -> Unit = {}
): Artifact {
  return artifact(
    artifactId = artifactId,
    version = version,
    configure = configure
  ).also {
    coEvery { repository.queryArtifact(eq(groupId), eq(it.id), eq(it.version)) } returns it
  }
}
