
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

### @Throws

* Java has checked exception. Exceptions inheriting from `RuntimeException` are unchecked.
* In Kotlin all exceptions are unchecked.
* Sometimes a Kotlin function needs to make Java aware, that it might throw a checked exception.

```kotlin
@Throws(IOException::class)
fun loadContent(fileName: String) {
  // ...
}
```
