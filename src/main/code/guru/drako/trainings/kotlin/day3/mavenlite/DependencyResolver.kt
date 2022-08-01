package guru.drako.trainings.kotlin.day3.mavenlite

import guru.drako.trainings.kotlin.day3.LoggerFactory
import guru.drako.trainings.kotlin.day3.debug
import guru.drako.trainings.kotlin.day3.error

/**
 * @property repositories The repositories to search for dependencies.
 */
class DependencyResolver(val repositories: List<Repository>) {
  companion object {
    private val logger by LoggerFactory
  }

  /**
   * Check the known [repositories] for the dependencies of the given [artifact].
   * Dependencies are resolved recursively.
   *
   * @param artifact The locally specified [Artifact] describing the current project.
   *
   * @throws ArtifactNotFoundException if the artifact could not be found anywhere.
   */
  suspend fun collectDependenciesOf(artifact: Artifact, seen: MutableSet<Dependency> = mutableSetOf()) {
    (artifact.dependencies - seen).forEach { dependency ->
      seen += dependency
      repositories
        .firstNotNullOfOrNull {
          it.queryArtifact(dependency.groupId, dependency.artifactId, dependency.version)
        }
        ?.also { collectDependenciesOf(it, seen) }
        ?: throw ArtifactNotFoundException("$dependency")
    }
  }
}
