package guru.drako.trainings.kotlin.day1

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
class FizzBuzzTest {
  @ParameterizedTest(name = "{index} => {0}")
  @ValueSource(ints = [3, 6, 9, 12, 18, 21, 96, 99])
  fun `numbers divisible by 3 should result in Fizz`(n: Int) {
    assertEquals(expected = "Fizz", actual = fizzBuzz(n))
  }

  @ParameterizedTest(name = "{index} => {0}")
  @ValueSource(ints = [5, 10, 20, 25, 85, 95, 100])
  fun `numbers divisible by 5 should result in Buzz`(n: Int) {
    assertEquals(expected = "Buzz", actual = fizzBuzz(n))
  }

  @ParameterizedTest(name = "{index} => {0}")
  @ValueSource(ints = [15, 30, 45, 60, 75, 90])
  fun `numbers divisible by 3 and 5 should result in FizzBuzz`(n: Int) {
    assertEquals(expected = "FizzBuzz", actual = fizzBuzz(n))
  }

  @ParameterizedTest(name = "{index} => {0}")
  @ValueSource(ints = [1, 2, 4, 7, 8, 11, 13, 14, 97, 98])
  fun `numbers neither divisible by 3 nor by 5 should result in themselves`(n: Int) {
    assertEquals(expected = "$n", actual = fizzBuzz(n))
  }
}
