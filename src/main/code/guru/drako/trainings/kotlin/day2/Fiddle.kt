package guru.drako.trainings.kotlin.day2

import java.io.PrintStream
import java.util.stream.Collectors


data class Config(
  var host: String = "localhost",
  var port: UShort = 80u,
  var numWorkerThreads: UInt = 8u,
)

fun scopeFunctions() {
  val cfg = Config().apply {
    port = 443u
    numWorkerThreads = 16u
  }


  val foo: Int? = 23

  val d = foo?.let { num -> num * 2 }

  // result of lambda is ignored
  val original = foo?.also { it * 2 }

  val d2 = foo?.run { this * 2 }
  val d3 = with(foo!!) {
    this * 2
  }

  // result of lambda is ignored
  val original2 = foo?.apply { this * 2 }
}

fun sequences() {
  val numbers = (1..100).shuffled()

  // expensive intermediate containers
  val results = numbers
    .map { it * 2 }
    .filter { it > 100 }
    .drop(10)
    .take(10)

  val a: List<Int> = numbers.map { it * 2 }
  val b: List<Int> = a.filter { it > 100 }
  val c: List<Int> = b.drop(10)
  val d: List<Int> = c.take(10)

  // use sequences for this
  val results2 = numbers.asSequence()
    .map { it * 2 }
    .filter { it > 100 }
    .drop(10)
    .take(10)
    .toList()

  // Java8 Stream API
  // somewhat equivalent to sequences, but not exactly
  val results3 = numbers.stream()
    .map { it * 2 }
    .filter {  it > 100 }
    .skip(10L)
    .limit(10L)
    .collect(Collectors.toList())

  val a2 = mutableListOf<Int>()
  var dropped = 0
  var taken = 0
  for (item in numbers) {
    val n = item * 2
    if (n > 100) {
      if (dropped < 10) {
        ++dropped
      } else if (taken < 10) {
        a2.add(n)
        ++taken
      } else {
        break
      }
    }
  }
  val d2: List<Int> = a2
}

interface Fooable {
  fun foo()
}

open class Foo {
  // can be overridden
  open fun ny() {}

  // cannot be overridden
  fun fact() {}

  companion object : Fooable {
    @JvmStatic
    override fun foo() {}

    @JvmField
    val answer = 42

    val pi = 3.1415926
      @JvmStatic get

    // const can only be used in static contexts
    // i.e. freestanding or in objects
    const val MESSAGE = "Hello world!"
  }
}

// language=Java
val javaFoo = """
  public final class Foo {
    private class Companion implements Fooable {
    
    }
  
    public static final Companion COMPANION = new Companion();
    
    public void foo() {}
  
    // @JvmStatic
    public static void foo() {
      COMPANION.foo();
    }
  }
""".trimIndent()

const val LEET = 1337

// object declaration
object Bar {
  fun bar() {}
}

// language=Java
val javaBar = """
  public final class Bar {
    private Bar() {}

    public static final Bar INSTANCE = new Bar();
    
    public void bar() {}
  
    // @JvmStatic
    public static void bar() {
      INSTANCE.bar();
    }
  }
""".trimIndent()

fun objects() {
  Foo.foo()

  println(Foo.answer)
  println(Foo.pi)
  println(Foo.MESSAGE)

  Bar.bar()
}


interface Printer {
  fun print(message: String)
}

fun streamPrinter(out: PrintStream = System.out): Printer {
  // object expression
  return object : Printer {
    // no const val here!
    override fun print(message: String) {
      out.print(message)
    }
  }
}

// language=Java
val javaObjectExpression = """
  import guru.drako.trainings.kotlin.day2.Printer;import org.jetbrains.annotations.NotNull;public class Main {
    public Printer streamPrinter(PrintStream out) {
      new Printer() {
        @Override
        void print(@NotNull String message) {
          out.print(message);
        }
      };
    }
  }
""".trimIndent()
