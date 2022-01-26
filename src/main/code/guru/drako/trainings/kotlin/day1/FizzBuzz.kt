package guru.drako.trainings.kotlin.day1

private fun fizz(n: Int): String = if (n % 3 == 0) "Fizz" else ""

private fun buzz(n: Int): String = if (n % 5 == 0) "Buzz" else ""

fun fizzBuzz(n: Int): String {
  return (fizz(n) + buzz(n)).ifEmpty { "$n" }
}
