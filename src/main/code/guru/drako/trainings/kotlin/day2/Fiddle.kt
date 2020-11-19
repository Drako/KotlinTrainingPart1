package guru.drako.trainings.kotlin.day2

import java.io.PrintStream

val numbers = listOf(1, 2, 3)
val doubled = numbers.map { it * 2 }

val squaredGreater5 = numbers
  .asSequence()
  .map { it * it }
  .filter { it > 5 }
  .reduce { buxtehude, itzehoe -> buxtehude + itzehoe }


val words = listOf("alpha", "bravo", "charlie")
val joined = words.joinToString(separator = ", ", prefix = "{", postfix = "}", transform = String::toUpperCase)

typealias Callback = () -> Unit

var foo = 24

fun fib() = sequence {
  var a = 0
  var b = 1
  while (true) {
    yield(a)
    a = b.also { b += a }
  }
}

inline fun myIf(condition: Boolean, block: () -> Unit) {
  if (condition) {
    block()
  }
}

interface Printer {
  fun print(message: String)
}

fun streamPrinter(out: PrintStream = System.out): Printer {
  return object : Printer {
    override fun print(message: String) {
      out.print(message)
    }
  }
}

fun main() {
  myIf(true) {
    return
  }

  fib()
    .take(10)
    .forEach(::println)
}
