package guru.drako.trainings.kotlin.day3.mavenlite

import guru.drako.trainings.kotlin.day3.LoggerFactory
import guru.drako.trainings.kotlin.day3.Optional
import guru.drako.trainings.kotlin.day3.debug
import guru.drako.trainings.kotlin.day3.error
import kotlinx.coroutines.runBlocking

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
    dependencyLoop@ for (dep in (artifact.dependencies - seen)) {
      seen += dep

      repositories
        .asSequence()
        .mapNotNullBlocking { it.queryArtifact(dep.groupId, dep.artifactId, dep.version) }
        .onEachBlocking { collectDependenciesOf(it, seen) }
        .firstOrNull() ?: throw ArtifactNotFoundException("$dep")
    }
  }

  fun <T, U> Sequence<T>.mapNotNullBlocking(block: suspend (T) -> U?): Sequence<U> {
    return mapNotNull { runBlocking { block(it) } }
  }

  fun <T> Sequence<T>.onEachBlocking(block: suspend (T) -> Unit): Sequence<T> {
    return onEach { runBlocking { block(it) } }
  }
}
