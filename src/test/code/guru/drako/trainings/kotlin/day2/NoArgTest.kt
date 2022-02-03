package guru.drako.trainings.kotlin.day2

import guru.drako.trainings.kotlin.Day2Test
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.should
import io.kotest.matchers.throwable.haveCauseInstanceOf
import io.kotest.mpp.newInstanceNoArgConstructor
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.*

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class NoArgTest {
  data class Point2D(val x: Int, val y: Int)

  @Test
  fun `Point2D cannot be constructed without arguments`() {
    assertThrows<InstantiationException> {
      Point2D::class.newInstanceNoArgConstructor()
    }.also { ex ->
      ex should haveCauseInstanceOf<NoSuchMethodException>()
    }
  }

  data class Point3D(val x: Int, val y: Int, val z: Int)

  @Test
  fun `Point3D can be constructed without arguments`() {
    assertDoesNotThrow {
      Point3D::class.newInstanceNoArgConstructor()
    }
  }
}
