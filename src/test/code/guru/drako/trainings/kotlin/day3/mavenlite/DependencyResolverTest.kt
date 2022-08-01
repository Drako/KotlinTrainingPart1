package guru.drako.trainings.kotlin.day3.mavenlite

import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day3Test)
class DependencyResolverTest {
  private val mavenCentral = mockk<Repository>()
  private val jcenter = mockk<Repository>()
  private val resolver = DependencyResolver(listOf(mavenCentral, jcenter))

  @BeforeEach
  fun setUp() {
    clearMocks(mavenCentral, jcenter)
    coEvery { mavenCentral.queryArtifact(any(), any(), any()) } returns null
    coEvery { jcenter.queryArtifact(any(), any(), any()) } returns null
  }

  @AfterEach
  fun tearDown() {
    confirmVerified(mavenCentral, jcenter)
  }

  @Test
  fun `Collecting dependencies for artifact with no dependencies should work`() {
    runBlocking {
      resolver.collectDependenciesOf(artifact("dummy", "0.1.0"))
    }
  }

  @Test
  fun `Collecting dependencies should work if dependency is in first repo`() {
    checkSingleDependencyFoundInRepository(mavenCentral)
  }

  @Test
  fun `Collecting dependencies should work if dependency is in second repo`() {
    checkSingleDependencyFoundInRepository(jcenter)
  }

  @Test
  fun `Collecting dependencies should fail if dependency is in neither repo`() {
    val myArtifact = artifact(artifactId = "my", version = "0.1.0") {
      +"guru.drako.example:dummy:0.1.0"
    }

    val ex = assertThrows<ArtifactNotFoundException> {
      runBlocking {
        resolver.collectDependenciesOf(myArtifact)
      }
    }

    ex.artifact shouldBe "guru.drako.example:dummy:0.1.0"
    coVerify { mavenCentral.queryArtifact(eq("guru.drako.example"), eq("dummy"), eq("0.1.0")) }
    coVerify { jcenter.queryArtifact(eq("guru.drako.example"), eq("dummy"), eq("0.1.0")) }
  }

  @Test
  fun `Collecting from different repos works`() {
    artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral)
    artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
    val myArtifact = artifact {
      id = "test"
      version = "0.1.0"
      +"guru.drako.example:a:0.1.0"
      +"guru.drako.example:b:0.1.0"
    }

    runBlocking {
      resolver.collectDependenciesOf(myArtifact)
    }

    coVerify(exactly = 2) { mavenCentral.queryArtifact(any(), any(), any()) }
    coVerify(exactly = 1) { jcenter.queryArtifact(any(), any(), any()) }
  }

  @Test
  fun `Collecting recursively works`() {
    artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
    artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral) {
      +"guru.drako.example:b:0.1.0"
    }
    val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
      +"guru.drako.example:a:0.1.0"
    }

    runBlocking {
      resolver.collectDependenciesOf(myArtifact)
    }

    coVerify(exactly = 2) { mavenCentral.queryArtifact(any(), any(), any()) }
    coVerify(exactly = 1) { jcenter.queryArtifact(any(), any(), any()) }
  }

  @Test
  fun `Collecting circular dependencies recursively works`() {
    artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral) {
      +"guru.drako.example:b:0.1.0"
    }
    artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter) {
      +"guru.drako.example:b:0.1.0"
    }
    val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
      +"guru.drako.example:a:0.1.0"
    }

    runBlocking {
      resolver.collectDependenciesOf(myArtifact)
    }

    coVerify(exactly = 2) { mavenCentral.queryArtifact(any(), any(), any()) }
    coVerify(exactly = 1) { jcenter.queryArtifact(any(), any(), any()) }
  }

  private fun checkSingleDependencyFoundInRepository(repository: Repository) {
    val firstRepo = mavenCentral.takeIf { repository === jcenter }

    val dummyArtifact = artifact("guru.drako.example", "dummy", "0.1.0", repository = repository)

    val myArtifact = artifact(artifactId = "my", version = "0.1.0") {
      +"guru.drako.example:dummy:0.1.0"
    }

    runBlocking {
      resolver.collectDependenciesOf(myArtifact)
    }

    with(dummyArtifact) {
      firstRepo?.let { repo ->
        coVerify { repo.queryArtifact(eq("guru.drako.example"), eq(id), eq(version)) }
      }
      coVerify { repository.queryArtifact(eq("guru.drako.example"), eq(id), eq(version)) }
    }
  }
}
