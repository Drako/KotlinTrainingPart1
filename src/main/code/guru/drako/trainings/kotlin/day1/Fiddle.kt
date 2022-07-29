@file:JvmName("Fiddle")
package guru.drako.trainings.kotlin.day1

/*
// language=Java
val j = """
  public class Point {
    private final int x;
    private final int y;
  
    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
    
    public int getX() {
      return x;
    }
    
    public int getY() {
      return y;
    }
    
    public void setX(final int x) {
      this.x = x;
    }
    
    public void setY(final int y) {
      this.y = y;
    }
  }
""".trimIndent()
*/

data class Point2D @JvmOverloads constructor(var x: Int = 0, var y: Int = 0) {
  init {
  }
  constructor(pt: String) : this(
    pt.split(",")[0].toInt(),
    pt.split(",")[1].toInt()
  ) {
    // executes after init{}
  }
}

class Foo {
  var foo: Int = 0
    private set
}

class MyList {
  class Item(var next: Item?)

  private var root: Item? = null
  val size: Int
    get() {
      var n = 0
      var currentNode = root
      while (currentNode != null) {
        ++n
        currentNode = currentNode.next
      }
      return n
    }

  fun add(value: Int) {
  }

  fun remove(value: Int) {
  }
}


fun asdasfsdaf() {
  val p = Point(23, 42)
  p.x = 1337
  p.x
}






@JvmOverloads
fun increase(n: Int, by: Int = 1) = n + by

fun main2() {
  val numbers1: MutableList<Int> = mutableListOf(1, 2, 3)
  numbers1 += 4
  //numbers1.plusAssign(4)

  // Try to avoid this
  var numbers2: List<Int> = listOf(1, 2, 3)
  numbers2 += 4
  // numbers2 = listOf(2, 3, 4)
  //numbers2 = listOf(1, 2, 3).plus(4)

  // NEVER do this
  var numbers3 = mutableListOf(1, 2, 3)

  increase(1)
  increase(1, 2)

  val i = MyKtInt()
  val j = MyKtInt()
  val k = i.plus(j)

  val i2 = FiddleJava()
  val j2 = FiddleJava()
  val k2 = i2 + j2

  val words = listOf("foo", "bar", "baz")
  for (word in words) {
    continue
    break
    return
  }

  words.forEach {
    return@forEach // continue
    return // actual return
  }
}

fun checkRange(min: Int, max: Int, n: Int) {
  if (!min.rangeTo(max).contains(n)) {

  }

  if (n !in min until max) {
    throw IndexOutOfBoundsException()
  }

  10 + 12
}

class MyKtInt {
  operator fun plus(other: MyKtInt): MyKtInt {
    return other
  }
}

class Fake {
  var value: String
    set(value) { println(value) }
    get() = readln()
}

data class Novaguple(
  val _1: Int,
  val _2: Int,
  val _3: Int,
  val _4: Int,
  val _5: Int,
  val _6: Int,
  val _7: Int,
  val _8: Int,
  val _9: Int
)
fun main() {
  val f = Fake()
  // println(readln())
  f.value = f.value

  val l = MyList()
  l.size

  val ll = listOf(1, 2, 3, 4, 5)
  val (one, _, three, four) = ll

  val p = Point2D(23, 321)
  val (alpha, bravo) = p
  val x = p.component1()
  val y = p.component2()

  val n = Novaguple(1, 2, 3, 4, 5, 6, 7, 8,9)
  n.component9()
}

data class SslConfig(
  val certificate: String,
  val key: String,
)

data class ServerConfig(
  val host: String,
  val port: UShort,
  val sslConfig: SslConfig?,
)

fun initSsl(config: SslConfig) {

}

fun setupServer(config: ServerConfig) {
  if (config.sslConfig != null) {
    initSsl(config.sslConfig)
  }
}

class Single<T>
class Pair<T1, T2>

class Triple<T1, T2, T3>
