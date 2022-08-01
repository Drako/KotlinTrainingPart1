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
    fun fromJson(json: String): Point = Json.decodeFromString(Point.serializer(), json)
  }

  fun toJson(): String = Json.encodeToString(Point.serializer(), this)
}
```

* We can make a class JSON serializable just by adding the `@Serializable` annotation
* There are other formats than JSON in development (CBOR, ProtoBuf, Properties)

See also:
* [Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization)
