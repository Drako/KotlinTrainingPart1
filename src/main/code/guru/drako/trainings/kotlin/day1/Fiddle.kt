package guru.drako.trainings.kotlin.day1

import kotlin.math.sqrt

fun add(a: Int, b: Int): Int {
  return a + b
}

infix fun Int.pow(exponent: Int): Int {
  var n = 1;
  repeat(times = exponent) {
    n *= this
  }
  return n
}

val kv = mapOf(
  "foo" to 23,
  "bar" to 1337
)

class NotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

class Point(val x: Int, val y: Int) {
  operator fun component1(): Int = x

  operator fun component2(): Int = y

  operator fun get(index: Int) = when (index) {
    0 -> x
    1 -> y
    else -> throw IndexOutOfBoundsException()
  }
}

fun test(p: Point?) {

  val distance = p?.run { sqrt((x * x + y * y).toDouble()) } ?: 0
}

interface Printer {
  fun print(message: String)
}

class ConsolePrinter : Printer {


  override fun print(message: String) {
    TODO("Not yet implemented")
  }
}

class Widget {
  val x = 23.0
  val y = 42.0

  fun fuzz() {
    val x = 50 // geshadowed

    val p = Point(10, 20)
    with (p) {
      val a: Double = this@Widget.x
    }
  }
}


fun foo(): Unit {
  // TODO:

  val numbers = listOf(1, 2, 3)
  val doubled = numbers.map { it * 2 }

  val number = 10
  val doubledN = number.let { it * 2 }


  numbers.forEach { n ->
    return@forEach
  }
}
