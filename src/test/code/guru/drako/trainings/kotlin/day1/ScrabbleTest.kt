package guru.drako.trainings.kotlin.day1

import guru.drako.trainings.kotlin.Day1Test
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day1Test)
class ScrabbleTest {
  @ParameterizedTest(name = "{index} => {0} should have the value {1}")
  @CsvFileSource(resources = ["scrabble_values.csv"])
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
    assertEquals(expected = expectedScore, actual = scrabbleScore(word))
  }
}
