# Day 3

For experiments:
 * [FiddleTest.kt](../src/test/code/guru/drako/trainings/kotlin/day3/FiddleTest.kt)
 * [Fiddle.kt](../src/main/code/guru/drako/trainings/kotlin/day3/Fiddle.kt)
 
For taking notes:
 * [Notes](./day3-notes.md)

## Gradle

* Maven can also be used to set up a Kotlin project
* Multiplatform projects are only supported by Gradle right now
* See [build.gradle.kts](../build.gradle.kts) and [settings.gradle.kts](../settings.gradle.kts)

### Exercise 6

* [DependencyResolverTest.kt](../src/test/code/guru/drako/trainings/kotlin/day3/mavenlite/DependencyResolverTest.kt)
* [DependencyResolver.kt](../src/main/code/guru/drako/trainings/kotlin/day3/mavenlite/DependencyResolver.kt)

Duration: 30 minutes

Try to implement the `collectDependenciesOf` function of `DependencyResolver`.
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
* [NoArg.kt](../src/main/code/guru/drako/trainings/kotlin/day3/NoArg.kt)
* [NoArgTest.kt](../src/test/code/guru/drako/trainings/kotlin/day3/NoArgTest.kt)

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

## @Throws

* Java has checked exception. Exceptions inheriting from `RuntimeException` are unchecked.
* In Kotlin all exceptions are unchecked.
* Sometimes a Kotlin function needs to make Java aware, that it might throw a checked exception.

```kotlin
@Throws(IOException::class)
fun loadContent(fileName: String) {
  // ...
}
```
