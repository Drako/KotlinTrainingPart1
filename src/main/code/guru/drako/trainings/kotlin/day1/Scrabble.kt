package guru.drako.trainings.kotlin.day1

private val ONES = setOf('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T')
private val TWOS = setOf('D', 'G')
private val THREES = setOf('B', 'C', 'M', 'P')
private val FOURS = setOf('F', 'H', 'V', 'W', 'Y')
private val FIVES = setOf('K')
private val EIGHTS = setOf('J', 'X')
private val TENS = setOf('Q', 'Z')

val LETTER_VALUES = mapOf(
  'A' to 1, 'E' to 1, 'I' to 1, 'O' to 1, 'U' to 1, 'L' to 1, 'N' to 1, 'R' to 1, 'S' to 1, 'T' to 1,
  'D' to 2, 'G' to 2,
  'B' to 3, 'C' to 3, 'M' to 3, 'P' to 3,
  'F' to 4, 'H' to 4, 'V' to 4, 'W' to 4, 'Y' to 4,
  'K' to 5,
  'J' to 8, 'X' to 8,
  'Q' to 10, 'Z' to 10
)

fun scrabbleScore(word: String): Int = word.sumOf { character ->
  LETTER_VALUES.getOrDefault(character, 0)
  /*
  when (character) {
    in ONES -> 1
    in TWOS -> 2
    in THREES -> 3
    in FOURS -> 4
    in FIVES -> 5
    in EIGHTS -> 8
    in TENS -> 10
    else -> 0
  }.toInt()
  */
}
