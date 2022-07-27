# Day 2

For experiments:
 * [FiddleTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/FiddleTest.kt)
 * [Fiddle.kt](../src/main/code/guru/drako/trainings/kotlin/day2/Fiddle.kt)
 
For taking notes:
 * [Notes](./day2-notes.md)

## More

### Objects

```kotlin
class Foo {
  companion object {
    fun foo() {}
  }
}

Foo.foo()
```

 * There is no `static`
 * The `companion object` is a singleton which can be used for static state
 * `@JvmStatic` & `@JvmField` can be used to make functions and fields really static for the JVM

```kotlin
object Foo {
  fun foo() {}
}

Foo.foo()
```

 * Just a singleton called `Foo`
 * Shortcut for when your class would only contain a `companion object`
 
```kotlin
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
```

 * Anonymous objects can be used to implement interfaces ad hoc
 * All `object`s can implement interfaces and extend classes
 
See also:
 * [Object Expressions and Declarations](https://kotlinlang.org/docs/reference/object-declarations.html) 

### Exercise 4

 * [RomanNumeralsConverterTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/RomanNumeralsConverterTest.kt)
 * [RomanNumeralsConverter.kt](../src/main/code/guru/drako/trainings/kotlin/day2/RomanNumeralsConverter.kt)

Duration: 1 hour

Try to implement the `RomanNumeralsConverter` class.
After you made all tests pass, try different ways of implementing it.

## Gradle

* Maven can also be used to set up a Kotlin project
* Multiplatform projects are only supported by Gradle right now
* See [build.gradle.kts](../build.gradle.kts)

## Compiler PlugIns

See also:
* [Compiler Plugins](https://kotlinlang.org/docs/reference/compiler-plugins.html)

### allopen

```kotlin
@Open
class Foo {
  fun frobnicate() {
    println("Foo")
  }
}

class Bar : Foo() {
  override fun frobnicate() {
    println("Bar")
  }
}
```

* By default, all classes & their member functions are `final`
* The "allopen" plugin allows us to specify marker annotations
* Classes marked with these annotations become completely `open`

### serialization

```kotlin
@Serializable
data class Point(val x: Int, val y: Int) {
  companion object {
    fun fromJson(json: String): Point = Json.decodeFromString(serializer(), json)
  }

  fun toJson(): String = Json.encodeToString(serializer(), this)
}
```

* We can make a class JSON serializable just by adding the `@Serializable` annotation
* There are other formats than JSON in development (CBOR, ProtoBuf, Properties)

### Exercise 5

 * [ShopTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/gildedrose/ShopTest.kt)
 * [Shop.kt](../src/main/code/guru/drako/trainings/kotlin/day2/gildedrose/Shop.kt)

Duration: 1 hour

Refactor the `Shop` class by first adding tests to `ShopTest`.
