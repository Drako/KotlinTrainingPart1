package guru.drako.trainings.kotlin.day2

import java.io.PrintStream
import kotlin.reflect.KClass

interface A
interface B

// Java:
// extends -> classes
// implements -> interfaces

open class Base

class Fiddle : A, B, Base() {
  inline fun <reified T: Any> create(): T {
    return T::class.constructors.first { it.parameters.isEmpty() }.call()
  }

  companion object Pisser : B {
    @JvmField
    val answer = 42

    @JvmStatic
    fun add(a: Int, b: Int) = a + b

    const val LEET = 1337
  }

  fun getIlluminati() = 23

  fun foo() {
    create<String>()

    var x: Nothing? = null
    x = null
    x = null

    FiddleJava().answer
    getIlluminati()

    val p1 = Point2D(23, 42)
    val p2 = Point2D(0, 100)
    val p3 = p1 + p2

    Thread { TODO("Not yet implemented") }.join()
  }
}

fun streamPrinter(out: PrintStream = System.out): Printer {
  return Printer { message -> out.print(message) }
}
