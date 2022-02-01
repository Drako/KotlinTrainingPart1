package guru.drako.trainings.kotlin.day3

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.channels.ProducerScope
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class CoroutineTest {
  @Test
  fun `fib generates first 10 numbers`() {
    fibonacci().take(10).toList() shouldBe listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
  }

  @Test
  fun `ints test`() {
    val scope = mockk<ProducerScope<Int>>()
  }
}