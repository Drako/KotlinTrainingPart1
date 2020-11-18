package guru.drako.trainings.kotlin.day1

private val ONES = setOf('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T')
private val TWOS = setOf('D', 'G')
private val THREES = setOf('B', 'C', 'M', 'P')
private val FOURS = setOf('F', 'H', 'V', 'W', 'Y')
private val FIVES = setOf('K')
private val EIGHTS = setOf('J', 'X')
private val TENS = setOf('Q', 'Z')

fun scrabbleScore(word: String): Int = word.sumBy { character ->
  when (character) {
    in ONES -> 1
    in TWOS -> 2
    in THREES -> 3
    in FOURS -> 4
    in FIVES -> 5
    in EIGHTS -> 8
    in TENS -> 10
    else -> 0
  }
}
