package guru.drako.trainings.kotlin.day2

import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.should
import io.kotest.matchers.throwable.haveCauseInstanceOf
import io.kotest.mpp.newInstanceNoArgConstructor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@TestInstance(Lifecycle.PER_CLASS)
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

  @NoArg
  data class Point3D(val x: Int, val y: Int, val z: Int)

  @Test
  fun `Point3D can be constructed without arguments`() {
    assertDoesNotThrow {
      Point3D::class.newInstanceNoArgConstructor()
    }
  }
}
