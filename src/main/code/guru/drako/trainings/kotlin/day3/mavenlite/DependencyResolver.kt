package guru.drako.trainings.kotlin.day3.mavenlite

/**
 * @property repositories The repositories to search for dependencies.
 */
class DependencyResolver(val repositories: List<Repository>) {
  /**
   * Check the known [repositories] for the dependencies of the given [artifact].
   * Dependencies are resolved recursively.
   *
   * @param artifact The locally specified [Artifact] describing the current project.
   *
   * @throws ArtifactNotFoundException if the artifact could not be found anywhere.
   */
  suspend fun collectDependenciesOf(artifact: Artifact) {
    TODO("Collect dependencies for $artifact")
  }
}
