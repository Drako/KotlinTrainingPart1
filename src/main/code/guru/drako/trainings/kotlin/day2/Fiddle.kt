package guru.drako.trainings.kotlin.day2

import java.io.PrintStream
import kotlin.math.sqrt
import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.reflect.KClass


enum class Color(val value: Int) {
  Red(0xFF0000),
  Green(0x00FF00),
  Blue(0x0000FF)
}

class Widget {
  inner class Node {

  }
}

inline fun <reified T: Any> create(): T {
  return T::class.java.newInstance()
}

val n = create<Widget>()

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

var foo = Color.Blue.value

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

  fun printLn(message: String)
}

object ConsolePrinter : Printer {
  override fun print(message: String) {
    System.out.print(message)
  }

  override fun printLn(message: String) {
    print(message + "\n")
  }

}

class ShoutingPrinter(val base: Printer): Printer by base {
  override fun print(message: String) {
    base.print(message.toUpperCase())
  }
}

/*
fun streamPrinter(out: PrintStream = System.out): Printer {
  return object : Printer {
    override fun print(message: String) {
      out.print(message)
    }
  }
}
*/

fun main() {
  val printer = ShoutingPrinter(base = ConsolePrinter)
  printer.print("hello\n")
  printer.printLn("hello")

  val p = Point3D(1, 2, 3)
  println(p.counter)
  p.counter = 42
  println(p.counter)
  p.counter = 23
  println(p.counter)

  val cfg = Config(mutableMapOf("port" to 80))
  cfg.host = "localhost"
  println(cfg.host)
  println(cfg.port)
}

class Config(data: MutableMap<String, Any>) {
  var host: String by data
  var port: Int by data
}


data class Point3D(val x: Int, val y: Int, val z: Int) {
  val distanceToZero: Double by lazy { sqrt((x * x + y * y + z * z).toDouble()) }

  var counter: Int by vetoable(23) { _, oldValue, newValue -> newValue > oldValue }
}
