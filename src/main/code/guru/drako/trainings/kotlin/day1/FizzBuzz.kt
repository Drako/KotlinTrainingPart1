package guru.drako.trainings.kotlin.day1

import java.util.Optional

private fun fizz(n: Int) = if (n % 3 == 0) "Fizz" else ""
private fun buzz(n: Int) = if (n % 5 == 0) "Buzz" else ""

inline fun <T> myIf(condition: Boolean, block: () -> T): Optional<T> {
  return if (condition) {
    Optional.of(block())
  } else {
    Optional.empty<T>()
  }
}

inline infix fun <T> Optional<T>.myElse(block: () -> T): T {
  return if (isEmpty) {
    block()
  } else {
    get()
  }
}

fun myAbs(n: Int) = myIf(n >= 0) {
  n
} myElse {
  -n
}

fun fizzBuzz(n: Int): String = (fizz(n) + buzz(n)).ifEmpty { "$n" }
