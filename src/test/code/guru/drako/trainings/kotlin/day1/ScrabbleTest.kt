package guru.drako.trainings.kotlin.day1

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
class ScrabbleTest {
  companion object {
    private val LETTER_VALUES = mapOf(
      'A' to 1, 'E' to 1, 'I' to 1, 'O' to 1, 'U' to 1, 'L' to 1, 'N' to 1, 'R' to 1, 'S' to 1, 'T' to 1,
      'D' to 2, 'G' to 2,
      'B' to 3, 'C' to 3, 'M' to 3, 'P' to 3,
      'F' to 4, 'H' to 4, 'V' to 4, 'W' to 4, 'Y' to 4,
      'K' to 5,
      'J' to 8, 'X' to 8,
      'Q' to 10, 'Z' to 10
    )

    @Suppress("unused")
    @JvmStatic
    fun testLetters(): Stream<Arguments> = LETTER_VALUES
      .entries
      .stream()
      .map { (letter, value) -> Arguments.of(letter, value) }
  }

  @ParameterizedTest(name = "{index} => {0} should have the value {1}")
  @MethodSource("testLetters")
  fun `letters should have their correct scores`(letter: Char, expectedScore: Int) {
    assertEquals(expected = expectedScore, actual = scrabbleScore("$letter"))
  }

  @ParameterizedTest(name = "{index} => {0} should have a score of {1}")
  @CsvSource(
    "APE, 5",
    "BREAD, 8",
    "CHEESE, 11",
    "DRAGON, 8",
    "HOUSE, 8",
    "MATRIX, 15",
    "MONKEY, 15",
    "PACHYCEPHALOSAURUS, 35"
  )
  fun `word score calculation should work`(word: String, expectedScore: Int) {
    assertEquals(expectedScore, scrabbleScore(word))
  }
}
