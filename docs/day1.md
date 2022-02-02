# Day 1 - Kotlin Basics

For experiments:
 * [FiddleTest.kt](../src/test/code/guru/drako/trainings/kotlin/day1/FiddleTest.kt)
 * [Fiddle.kt](../src/main/code/guru/drako/trainings/kotlin/day1/Fiddle.kt)
 
For taking notes:
 * [Notes](./day1-notes.md)

## Fundamentals

### Functions

```kotlin
fun add(a: Int, b: Int): Int {
  return a + b
}
```

 * Functions start with the `fun` keyword
 * Types come after the name
 * Parameters are implicitly `final`
              
```kotlin
fun subtract(a: Int, b: Int) = a - b
```

 * If the body of the function is just a single expression, we don't need the braces
 * In that case the return type is optional as the compiler can deduce it from the expression
 
```kotlin
fun increase(n: Int, by: Int = 1) = n + by
```

 * We can declare default values for missing parameters
 * Parameters with default values do not need to be at the end

```kotlin
increase(22)           // 23
increase(40, 2)        // 42
increase(1330, by = 7) // 1337
increase(n = 1)        // 2
```

 * Parameters can also be named explicitly
 * When using names the order does not need to match the order in the declaration
 * There can be no positional parameters after the first named parameter
 
```kotlin
"23".toInt()   // 23
42L.toString() // "42"
```

 * We call member functions by using the dot operator.
 * Most core types have member functions allowing conversions between them.
   (No implicit conversions!)
 
See also:
 * [Functions](https://kotlinlang.org/docs/reference/functions.html)

## Operators

Very short overview:

```kotlin
// equality
a == b
a != b

// identity
a === b
a !== b

// comparison
a < b
a <= b
a > b
a >= b

// calculation
a + b
a - b
a * b
a / b
a % b
```

See also:
 * [Operators and Special Symbols](https://kotlinlang.org/docs/reference/keyword-reference.html#operators-and-special-symbols)
 * [Operator Overloading](https://kotlinlang.org/docs/reference/operator-overloading.html)

### Control Structures

```kotlin
fun abs(n: Int) = if (n < 0) -n else n
```

 * If both branches of an if-else are given, the entire structure is an expression
 * There is no ternary operator (`condition ? true-case : false-case`)
 
```kotlin
fun checkRange(min: Int, max: Int, n: Int) {
  if (n !in min..max) {
    throw IndexOutOfBoundsException()
  }
}
```

 * If we use only `if`, that's a statement.
 
```kotlin
when (c) {
  'a' -> 1
  'b' -> 2
  'c' -> 3
  else -> 0
}
```

 * If a `when` is exhaustive (i.e. we covered all cases), the entire structure is an expression

```kotlin
fun max(a: Int, b: Int) = when {
  a > b -> a
  a < b -> b
  else -> throw IllegalStateException("a and b are equal")
}
```

 * We can use `when` to simplify chains of `if-else if-else if-else`

```kotlin
for (n in 1..10) {
  println(n)
}
```

 * There is no C-Style for-loop
 * `for` supports any iterable data structures (e.g. containers and ranges)

```kotlin
while (a > b) {
  // condition checked first
}

do {
  // condition checked last
} while (a < b)
```

 * `while` and `do-while` loops behave like expected

### Exercise 1

 * [FizzBuzzTest.kt](../src/test/code/guru/drako/trainings/kotlin/day1/FizzBuzzTest.kt)
 * [FizzBuzz.kt](../src/main/code/guru/drako/trainings/kotlin/day1/FizzBuzz.kt)

Duration: 15 minutes

Try to implement the `fizzBuzz` function.
After you made all tests pass, try different ways of implementing it.

## Mutability

### Variables

```kotlin
val a = 23
val b: String = "foo"

var c = 42
c = 1337
```

 * We declare constants with `val` for value
 * We declare variables with `var`
 
```kotlin
const val MESSAGE = "Hello world!"
```

 * For compile time constants of primitive types we have `const val`

**Rule of thumb**: Always start with `val` and only choose `var` when necessary.

### Collections

```kotlin
// immutable
val numbers = listOf(1, 2, 3, 4)

val squares = mapOf(1 to 1, 2 to 4, 3 to 9)

// mutable
val fib = mutableListOf(1, 2, 3, 5, 8, 13, 21)
fib += 34

val magicNumbers = mutableMapOf("illuminati" to 23, "leet" to 1337)
magicNumbers["answer"] = 42
```

Collections come in mutable and immutable versions:
 * `List<T>` & `MutableList<T>`
 * `Map<Key, Value>` & `MutableMap<Key, Value>`
 * `Set<T>` & `MutableSet<T>`

**Rule of thumb**: Always start with immutable collections and only choose mutable ones when necessary.

### Classes

```kotlin
class Greeter(
    private val out: PrintStream,
    var target: String = "world"
) {
  fun greet() {
    out.println("Hello, $target!")
  }
}

val greeter = Greeter()
greeter.greet()             // prints "Hello, world!\n"

greeter.target = "Felix"
greeter.greet()             // prints "Hello, Felix!\n"
```

 * `val` in the primary constructor generates a constructor parameter, a backing field and a getter
 * `var` in the primary constructor additionally generates a setter
 * Again **prefer immutable members over mutable ones**

### Data Classes

```kotlin
data class Point(val x: Int, val y: Int)

val p1 = Point(x = 10, y = 20)
val p2 = p1.copy(x = 20)

val (x, y) = p1

p1.toString()

p1 == p2
```

 * data classes have autogenerated `equals`, `hashCode` and `toString` functions.
 * they get a special `copy` function to make copies with slight modifications,
   useful for working with immutable data classes
 * we can use them in destructuring assignments

### Exercise 2

 * [ScrabbleTest.kt](../src/test/code/guru/drako/trainings/kotlin/day1/ScrabbleTest.kt)
 * [Scrabble.kt](../src/main/code/guru/drako/trainings/kotlin/day1/Scrabble.kt)

Duration: 15 minutes

Try to implement the `scrabbleScore` function.
After you made all tests pass, try different ways of implementing it.

## Nullity

### Variables & Constants

```kotlin
var foo: String? = null
foo = "hello"
```

 * Variables and constants that might be `null` have a question mark behind the type
 * The possibility to become `null` is an integral part of the type system

### Smart Cast

```kotlin
val foo: Foo? = maybeNull()
if (foo != null) {
  foo.frobnicate()
}
```

 * After checking for `null` the type will smartly be cast to its non-nullable version
 * Members can only be accessed on non-nullable values; the compiler enforces these checks
 
### Elvis Operator (Fallback operator)

```kotlin
val foo: String = maybeNull() ?: "unknown"
val bar: String = maybeNull() ?: throw MissingValueException()

fun baz() {
  val config = getConfig() ?: return
  // do stuff with config
}
```

 * The elvis operator returns the result of its right side if its left side is `null`
 * It can also be chained if there are multiple nullable alternatives
 
### Safe-Call Operator

```kotlin
data class Point(val x: Int, val y: Int)

val p = maybePoint()
val x = p?.x ?: 0
```

 * The safe-call-operator `?.` only calls its right side when the value on the left is not `null`
 * The type of the entire expression is nullable again
 * Safe-calls can be chained
 * The first occurrence of `null` cuts the chain short
 * It can be combined with the elvis-operator

### Not-Null-Assertion Operator

```kotlin
val a = maybeNull()
val b = a!!
```

 * Only use if you are absolutely sure your value is never `null` even though the type is nullable
 * Even then this usually implies there might be something wrong with your data model
 * Should be avoided in production code
 * Okay in test code when `null` values should make the test fail anyway

### Scope functions

Kotlin comes with a bunch of scope functions which can be used on any class.
These are especially useful with the safe-call operator.

```kotlin
val p = maybePoint()

val distance = p?.let { (x, y) -> sqrt(x * x + y * y) } ?: 0

var a = 10
var b = 20
a = b.also { b = a }

class Config {
  var host: String = "localhost"
  var port: Int = 8000
}

val cfg = Config().apply {
  host = "drako.guru"
  port = 443
}

with(cfg) {
  println("$host:$port")
}
```

See also:
 * [Scope Functions](https://kotlinlang.org/docs/reference/scope-functions.html)

### Exercise 3

 * [LinkedListTest.kt](../src/test/code/guru/drako/trainings/kotlin/day1/LinkedListTest.kt)
 * [LinkedList.kt](../src/main/code/guru/drako/trainings/kotlin/day1/LinkedList.kt)

Duration: 45 minutes

Try to implement the `LinkedList` class.
After you made all tests pass, try different ways of implementing it.

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
