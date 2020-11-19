package guru.drako.trainings.kotlin.day2.mavenlite

import guru.drako.trainings.kotlin.Day2Test
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class DependencyResolverTest {
  private val mavenCentral = mockk<Repository>()
  private val jcenter = mockk<Repository>()
  private val resolver = DependencyResolver(setOf(mavenCentral, jcenter))

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
  }

  @Test
  fun `Collecting from different repos works`() {
    artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral)
    artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
    val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
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
    artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral) {
      +"guru.drako.example:b:0.1.0"
    }
    artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
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
    val dummyArtifact = artifact("guru.drako.example", "dummy", "0.1.0", repository = repository)

    val myArtifact = artifact(artifactId = "my", version = "0.1.0") {
      +"guru.drako.example:dummy:0.1.0"
    }

    runBlocking {
      resolver.collectDependenciesOf(myArtifact)
    }

    with(dummyArtifact) {
      coVerify { repository.queryArtifact("guru.drako.example", id, version) }
    }
  }

  @Disabled("These are just here to make sure that the mocking with coroutines works as expected, they don't need to be run all the time.")
  @Nested
  inner class CheckTestEnvironment {
    @Test
    fun `Not adjusting the mocks should not crash badly`() {
      assertDoesNotThrow {
        runBlocking {
          mavenCentral.queryArtifact("guru.drako.example", "mavenlite", "0.1.0")
          jcenter.queryArtifact("guru.drako.example", "mavenlite", "0.1.0")
        }
      }
    }

    @Test
    fun `Mocking coroutines should work as expected`() {
      val dummyArtifact = Artifact(
        id = "dummy",
        version = "0.1.0"
      )

      coEvery { mavenCentral.queryArtifact(any(), eq("dummy"), eq("0.1.0")) } returns dummyArtifact

      runBlocking {
        mavenCentral.queryArtifact("guru.drako.example", "dummy", "0.1.0").shouldNotBeNull()
        mavenCentral.queryArtifact("guru.drako.example", "dummy", "0.0.1") should beNull()
        mavenCentral.queryArtifact("guru.drako.example", "foo", "0.1.0") should beNull()
        mavenCentral.queryArtifact("com.example", "dummy", "0.1.0").shouldNotBeNull()
      }
    }
  }
}
