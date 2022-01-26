@file:JvmName("Fiddle")

package guru.drako.trainings.kotlin.day1

import kotlin.math.sqrt
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun Float.squared() = this * this

val s = 23f.squared()

data class SslConfig(val certificate: String, val key: String)

data class ServerConfig(
  val ip: String,
  val port: UShort,
  val sslConfig: SslConfig? = null
)

class ServerConfigBuilder(
  var ip: String? = null,
  var port: UShort? = null,
  var sslConfig: SslConfig? = null
)

fun config(block: ServerConfigBuilder.() -> Unit): ServerConfig {
  return ServerConfigBuilder().apply(block).let {
    ServerConfig(
      ip = it.ip ?: "::1",
      port = it.port ?: (if (it.sslConfig != null) 443 else 80).toUShort(),
      sslConfig = it.sslConfig
    )
  }
}

fun loadCertificate(): String? = TODO()

fun configure() {
  val cert = loadCertificate()
  val cfg = config {
    ip = "127.0.0.1"
    if (cert != null) {
      sslConfig = SslConfig(cert, "foo")
    }
  }
}

//fun String?.orEmpty() = this ?: ""

fun <T> T.toStringTwice() = "$this$this"

val ServerConfig.url: String
  get() = sslConfig?.let { "https://$ip:$port" } ?: "http://$ip:$port"

inline fun myIf(condition: Boolean, block: () -> Unit) {
  if (condition) {
    block()
  }
}

//language=js
val x = """
function myIf(cond, block) {
    if (cond) { block(); }
}

function foo() {
    myIf(true, function() { return; });
}
"""

inline fun <reified T : Any> create(): T {
  return T::class.primaryConstructor!!.call()
}

fun <T : Any> create(clazz: KClass<T>): T {
  return clazz.primaryConstructor!!.call()
}

inline fun <T : Any> T.myApply(block: T.() -> Unit) {
  block()
}

fun loadConfig(cfg: ServerConfig) {
  cfg.myApply {
    ip
    port
  }

  val getUrl: ServerConfig.() -> String = {
    sslConfig?.let { "https://$ip:$port" } ?: "http://$ip:$port"
  }

  cfg.getUrl()

  for (v in listOf<Int>()) {
    return
  }

  myIf(true) {
    println("Hello")
    return
  }

  val s: String? = null
  val x: String = s.orEmpty() // ?: ""

  if (cfg.sslConfig != null) {
    cfg.sslConfig.key
  }

  cfg.url
  cfg.toStringTwice()

  cfg.sslConfig?.also { (cert, key) -> println(cert) }
}

//language=java
val javaCode = """
public final class Point {
  private int x;
  private final int y;
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
}
"""

class Point(var x: Int, val y: Int, internallyDoubleValue: Int = 0) {
  fun useX(x: Int? = null) {
    println(x ?: this.x)
  }

  override fun toString(): String {
    return "Point[x = $x, y = $y]"
  }

  val length: Double
    get() = sqrt((x * x + y * y).toDouble())

  var value: String
    set(value) {
      println(value)
    }
    get() = readln()

  var internallyDoubleValue: Int = internallyDoubleValue
    set(value) {
      field = value * 2
    }
    get() = field / 2
}

var GLOBAL_POINT: Point? = null

fun test() {
  val gp = GLOBAL_POINT
  if (gp != null) {
  }

  val p: Point? = Point(23, 42)
  if (p != null) {
    p.x = 1337
    p.length

    p.value = "Hello world!"
    val s = p.value

    println(p.x)
  }

}

/*        java        kotlin
          private   <-> private
(package) protected <-> protected
          public    <-> public <- default, wenn nichts da steht
 (package private)  <-> gibt's nicht
       gibt's nicht <-> internal
 */



sealed class MyList<T>
class End<T> : MyList<T>()
class Item<T>(val value: T, val next: MyList<T>)

sealed class Tree

class Leaf : Tree()
class Node(val left: Tree, val right: Tree) : Tree()

class MainApplication

const val ANSWER = 42

fun add(a: Int, b: Int) = a + b

fun iterateWithIndex(numbers: List<Int>) {
  for ((idx, v) in numbers.withIndex()) {

  }
}

// const n = 42;
// const obj = {};
// obj.foo = 23;

fun numType(n: Int): String {

  return if (n % 2 == 0) {
    "even"
  } else {
    "odd"
  }
}

/*
 int -> Integer     : Int
 long -> Long       : Long
 float -> Float     : Float
 double -> Double   : Double
 primitive -> Boxed : Kotlin

 String             : String
 */
