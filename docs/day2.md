# Day 2

## More

### Sequences

```kotlin
val quads = (1..Int.MAX_VALUE)
    .asSequence()
    .map { it * it }
    .takeWhile { it < 1000 }
    .toList()
```

 * Use for chaining operations on containers.
 * Any iterable can be treated as a sequence.
 

See also:
 * [Sequences](https://kotlinlang.org/docs/reference/sequences.html)

### Extension Functions

```kotlin
fun Int.square() {
  return this * this
}

val a = 2
val b = a.square()
```

 * We can extend existing classes with custom functions and properties without touching them
 * Inside an extension function the so called "receiver" is `this`
 
```kotlin
fun <T> T.toStringUpperCase() = toString().toUpperCase()
```

 * The receiver can be generic
 
```kotlin
fun <T> T.apply(block: T.() -> Unit) {
  block()
}
```

 * Lambdas can also be extension functions

See also:
 * [Extensions](https://kotlinlang.org/docs/reference/extensions.html)

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

Duration: 45 minutes

Try to implement the `RomanNumeralsConverter` class.
After you made all tests pass, try different ways of implementing it.
