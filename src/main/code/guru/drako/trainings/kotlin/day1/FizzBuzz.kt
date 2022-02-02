package guru.drako.trainings.kotlin.day1



private fun fizz(n: Int) = if (n % 3 == 0) "Fizz" else ""
private fun buzz(n: Int) = if (n % 5 == 0) "Buzz" else ""

fun fizzBuzz(n: Int): String {
  return (fizz(n) + buzz(n)).ifEmpty { "$n" }
}

/*
 "1"
 2
 "Fizz"
 4
 "Buzz"
 Fizz
 7
 8
 Fizz
 Buzz
 ..
 15 -> FizzBuzz
 */
