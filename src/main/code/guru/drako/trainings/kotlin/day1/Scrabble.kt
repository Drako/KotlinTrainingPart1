package guru.drako.trainings.kotlin.day1

private val LETTER_SCORES = mapOf(
  'A' to 1, 'E' to 1, 'I' to 1, 'O' to 1, 'U' to 1,
  'L' to 1, 'N' to 1, 'R' to 1, 'S' to 1, 'T' to 1,
  'D' to 2, 'G' to 2,
  'B' to 3, 'C' to 3, 'M' to 3, 'P' to 3,
  'F' to 4, 'H' to 4, 'V' to 4, 'W' to 4, 'Y' to 4,
  'K' to 5,
  'J' to 8, 'X' to 8,
  'Q' to 10, 'Z' to 10,
)

fun printException(ex: Throwable) {
  if (ex is IllegalArgumentException) {
    println("Illegal argument")
  }
  else if (ex is NullPointerException) {
    println("Null pointer")
  }
  
  when (ex) {
    is IllegalArgumentException -> {
      println("Illegal argument")
    }
    is NullPointerException -> println("Null pointer")
  }
}

fun scrabbleScore(word: String): Int = word.sumOf { LETTER_SCORES[it] ?: 0 }
