package guru.drako.trainings.kotlin.day3

import guru.drako.trainings.kotlin.day2.x
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.stream.Stream

/*
@NoArg
data class Point(val x: Int, val y: Int) {
  init {

  }

  constructor() : this(23, 42) {

  }

  companion object {
    fun fromString(str: String): Point? = Point(

    )
  }
}
*/

fun foo() {
  // morphia bad
  val p = Point::class.java.getConstructor().newInstance()
  Point::class.java.getField("x").apply {
    isAccessible = true
    set(p, 23)
  }
  Point::class.java.getField("y").apply {
    isAccessible = true
    set(p, 42)
  }
}

@Open(description = "for mocking")
class Foo {
  fun foo() {}
}

class Bar : Foo() {
  override fun foo() {

  }
}

/*
fun main() {
  for (c in getAllClassesInProject()) {
    val annotations = c.getAnnotationsByType(Open::class.java)
    if (annotations.size > 0) {
      makeOpen(c)
    }
  }
}
*/

data class Person(
  val id: Int,
  val firstName: String,
  val lastName: String
) {
  companion object {
    fun fromPackage(pkg: PersonPackage, id: Int) = Person(
      id = id,
      firstName = pkg.firstName,
      lastName = pkg.lastName
    )
  }
}

// /persons/{id}
// {firstName: "X", lastName: "Y"}
data class PersonPackage(val firstName: String, val lastName: String)

@Serializable
data class Dimension(val name: String)

@Serializable
data class Point(val x: Int = 10, val y: Int = 10, val dim: Dimension? = null) {
  companion object {
    fun fromJson(json: String): Point = Json.decodeFromString(json)
  }

  fun toJson(): String = Json.encodeToString(this)
}

fun baz() {
  val p = Point2D<Int>()
  State.Running.fieldName
  State.Done.name
}


enum class State(val fieldName: String) {
  Running(fieldName = "running"),
  Done(fieldName = "done")
}

sealed class Optional<T>
object None : Optional<Any?>()
data class Some<T>(val value: T) : Optional<T>()

fun <T> none(): Optional<T> {
  return None as Optional<T>
}

fun <T> some(value: T): Optional<T> {
  return Some(value)
}

fun maybe(): Optional<Int> = TODO()

fun check() {
  val x = maybe()

  // Java
  // if (x instanceof Some<Int>) {
  //   final var someX = (Some<Int>)x;

  when (x) {
    is Some<Int> -> println("Value: ${x.value}")
    else -> println("No Value")
  }
}
