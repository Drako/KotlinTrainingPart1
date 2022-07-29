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

### Exercise 5

 * [ShopTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/gildedrose/ShopTest.kt)
 * [Shop.kt](../src/main/code/guru/drako/trainings/kotlin/day2/gildedrose/Shop.kt)

Duration: 1 hour

Refactor the `Shop` class by first adding tests to `ShopTest`.
