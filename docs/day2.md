# Day 2

For experiments:
 * [FiddleTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/FiddleTest.kt)
 * [Fiddle.kt](../src/main/code/guru/drako/trainings/kotlin/day2/Fiddle.kt)
 
For taking notes:
 * [Notes](./day2-notes.md)

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

Duration: 1 hour

Try to implement the `RomanNumeralsConverter` class.
After you made all tests pass, try different ways of implementing it.

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

### noarg

Some libraries require data structures that can
be constructed with no parameters via reflection.

See:
 * [NoArg.kt](../src/main/code/guru/drako/trainings/kotlin/day2/NoArg.kt)
 * [NoArgTest.kt](../src/test/code/guru/drako/trainings/kotlin/day2/NoArgTest.kt)

### serialization

```kotlin
@Serializable
data class Point(val x: Int, val y: Int) {
  companion object {
    fun fromJson(json: String): Point = JSON.parse(Point.serializer(), json)
  }

  fun toJson(): String = JSON.stringify(JSON.serializer(), this)
}
```

 * We can make a class JSON serializable just by adding the `@Serializable` annotation
 * There are other formats than JSON in development (CBOR, ProtoBuf, Properties)
 
See also:
 * [Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization)

## Java InterOp

### @JvmStatic & @JvmField

```kotlin
object Foo {
  @JvmField val data = mapOf("pi" to 3.1415926)

  @JvmStatic fun sayHello() = println("Hello World!")
}
```

```java
public class Main {
  public static void main(String[] args){
    Foo.data;
    Foo.sayHello();
  }
}
```

 * There is no `static` in Kotlin
 * To make something actually `static` we use `@JvmStatic` for functions
   and `@JvmField` for fields

### @JvmOverloads

```kotlin
class Greeter {
  @JvmOverloads
  fun sayHello(target: String = "world") {
    println("Hello, $world!")
  }
}
```

```java
public class Main {
  public static void main(String[] args){
    final Greeter greeter = new Greeter();
    greeter.sayHello();
    greeter.sayHello("Felix");
  }
}
```

 * Kotlin supports arguments with default values
 * Java doesn't
 * If `@JvmOverloads` is present, the compiler generates function overloads
   so Java can also profit from specified default values
   
### @Nullable & @NotNull

 * When interacting with Java, Kotlin usually does not know whether a type is nullable or not
 * The Kotlin compiler recognizes a wide array of annotations to figure our nullity
 * If it is unclear we get a so called "platform type"
