@file:JvmName("Fiddle")
package guru.drako.trainings.kotlin.day1

import kotlin.math.sqrt

//language=JS
val h = """
  const x = {};
  x.foo = 23;
""".trimIndent()

//language=Java
val j = """
  import java.util.*;
  
  class X {
    public static void main(final String[] args){
      final List<Integer> l = new ArrayList<>();
      final List<Integer> cl = Collections.unmodifiableList(l);
      cl.add(42);
    }
  }
""".trimIndent()

class Foo {
  operator fun component1(): Int = 42

  companion object {
    const val ANSWER = 42

    fun position() = 23 to 42

    fun foo() {
      val fib = mutableListOf<Int>(1, 2, 3, 5, 8, 13, 21)
      fib += 34
      val (first, second, third, fourth, fifth) = fib
      println(fifth)

      val (x, y) = position()
    }
  }
}

// val y = Foo.foo()
fun dummy() {
  val (answer) = Foo()
}

/*
 Java                            Kotlin
 public                          (public)
 private                         private   (only class itself)
 (package-)protected                       (only class, subclasses and same package)
 - not existent -                protected (only class and subclasses)
 (package-private)               - not existent - (only class and same package)
 - not existent -                internal (public, but only visible in same module)
 */

fun a() = 23
fun b() = a()
fun c() = b()

fun bar() {
  for (n in 1..10 step 2) {

  }
}

// ß    &szlig;

fun subtract(a: Int, b: Int): Int = a - b
/*
  int subtract(final int a, final int b) {
    return a - b;
  }
 */

/*
 Java                    Kotlin
 int    Integer    ->    Int
 */



// x.toString()
// "$x"

// "${42 + 23}"
// (42 + 23).toString()

// val x = "The result is $result"

// language=Java
val javaCode = """
  public final class Point {
    private final int x;
    private final int y;
    
    public Point(final int x, final int y) {
      this.x = x;
      this.y = y;
    }
    
    public Point(final int x) {
      this(x, 0);
    }
    
    public Point() {
      this(0);
    }
    
    public int getX() {
      return x;
    }
    
    public int getY() {
      return y;
    }
    
    public void setY(final int y) {
      this.y = y;
    }
  }
""".trimIndent()

data class Point @JvmOverloads constructor(val x: Int = 0, var y: Int = 0) : Any() {


  var callCount: Int = 0
    private set

  companion object {
    fun fromPair(p: Pair<Int, Int>) = Point(p.first, p.second)
  }
}

val Point?.length: Double
  get() = this?.let { sqrt((it.x * it.x + it.y * it.y).toDouble()) } ?: 0.0

fun main() {
  val p = Point(23, 42)
  val str: String? = ""
  str.isNullOrBlank()
  println(p)
}

var gp: Point? = null

fun example() {
  val p: Point? = Point()
  if (p != null) {
    p.length
  }

  val lgp = gp
  if (lgp != null) {
    lgp.length
  }
}

data class SslConfig(
  val certificate: String,
  val key: String,
)

data class ServerConfig(
  var host: String,
  val port: UShort,
  val sslConfig: SslConfig? = null
)

fun applyConfig(config: ServerConfig) {
  if (config.sslConfig != null) {
    config.sslConfig.key
  }

  config.sslConfig?.also { sslConfig -> println(sslConfig.key) }

  val answer = with(config) {
    host = "bar"
    42
  }

  val a: ServerConfig = config.apply {
    sslConfig!!.key
    host = ""
  }

  val c: ServerConfig = config.also {
    it.host = "foo"
  }

  val b: Int = config.run {
    42
  }

  val port: UShort = config.let(ServerConfig::port.getter)
}

typealias Predicate<T> = (T) -> Boolean


