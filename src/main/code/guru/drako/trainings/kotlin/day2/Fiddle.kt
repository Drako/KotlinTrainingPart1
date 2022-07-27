package guru.drako.trainings.kotlin.day2

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.DoubleSummaryStatistics
import kotlin.coroutines.*
import kotlin.math.sqrt

@RestrictsSuspension
class NoSuspension

// sealed
// -> nur in der selben Datei kann geerbt werden
sealed class ExecutionResult<T>

class ExecutionFailure(val exception: Throwable) : ExecutionResult<Any?>()

class ExecutionSuccess<T>(val result: T) : ExecutionResult<T>()

fun <T> justRun(block: suspend NoSuspension.() -> T): T {
  var coroutineResult: ExecutionResult<T>? = null

  val coroutine = block.createCoroutine(NoSuspension(), object : Continuation<T> {
    override val context: CoroutineContext
      get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<T>) {
      coroutineResult = result.getOrNull()?.let { ExecutionSuccess(it) }
        ?: result.exceptionOrNull()?.let {
          @Suppress("UNCHECKED_CAST")
          ExecutionFailure(it) as ExecutionResult<T>
        }
    }
  })

  coroutine.resume(Unit)

  return when (val result = coroutineResult) {
    is ExecutionSuccess<T> -> result.result
    is ExecutionFailure -> throw result.exception
    null -> throw IllegalStateException()
  }
}

suspend fun NoSuspension.answer(): Int {
  return 6 * 7
}

fun main() {
  val answer = justRun { answer() }
  println("The answer is $answer")
}


// language=Java
val javaInterface = """
  interface Sizable {
    int getSize();
  }
""".trimIndent()

interface Sizable {
  val size: Int

  fun resize(newSize: Int)
}

abstract class BaseSizable : Sizable {

}

@JvmInline
value class ID(val value: Int) {
  init {
    require(value in 0..10000)
  }
}

enum class State {
  Saxony,
  Bavaria,
  Thuringia,
}

@JvmInline
value class PLZ(val value: Int) {
  init {
    require(value in 0..99999)
  }

  fun state(): State {
    return State.Saxony // wrong
  }

  override fun toString(): String {
    return "${value / 10000}${(value / 1000) % 10}${(value / 100) % 10}${(value / 10) % 10}${value % 10}"
  }
}

class Book(val id: Int)

object Math : Sizable {
  init {
    val r = object : Runnable {
      override fun run() {
        TODO("Not yet implemented")
      }
    }

    r.run()
  }

  @JvmStatic
  fun sqrt(number: Double) {
  }

  override val size: Int
    get() = 42

  override fun resize(newSize: Int) {
    TODO("Not yet implemented")
  }
}


class Foo {
  companion object {
    @JvmField
    val bar = 23

    @JvmStatic
    fun say() {
    }

    // implicitly static final
    const val ANSWER = 42
  }

  fun fidget() {
    say()
  }
}

fun foo() {
  Foo.bar

  Foo.say()

  val p = Point2D(1.0, 1.0)
  p.x
}

class X : Droppable {
  override fun getDropped(): Boolean {
    TODO("Not yet implemented")
  }
}


@Serializable
data class Point(val x: Int, val y: Int) {
  companion object {
    fun fromJson(json: String): Point = Json.decodeFromString(serializer(), json)
  }

  fun toJson(): String = Json.encodeToString(serializer(), this)
}

@Open
class Widget {
  fun render() {}
}

class Button : Widget() {
  override fun render() {}
}

@Serializable
data class Name(val value: String)

fun Name.toJson() {
  Json { ignoreUnknownKeys = false }
}

// language=Java
val javaForEach = """
  import java.util.*;  
  
  class Example {
    void runLoop(final List<Int> numbers) {
      numbers.forEach(it -> {
        return; // continue
      });
    }
  }
""".trimIndent()

fun frobnicate() {
  for (it in 1..100) {
    continue // skips to next cycle
    break // breaks out of the loop
    return // returns from function entirely
    println(it)
  }

  outer@ for (x in 1..10) {
    for (y in 1..10) {
      break@outer
    }
  }

  repeat(10) {

  }

  with(javaForEach) {

    (1..100).forEach {
      return@forEach // continue
      return
      return@with
      println(it)
    }
  }

  val answer: Int? = FiddleJava.answer()
}
