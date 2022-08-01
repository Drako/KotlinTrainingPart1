package guru.drako.trainings.kotlin.day3

import kotlinx.serialization.Serializable

@Open
@Serializable
data class Foo(val a: Int)

class Bar : Foo(23)

@NoArg
data class Person2(
  val firstName: String,
  val lastName: String
)

fun main() {
  val kcls = Person2::class
  val cls = Person2::class.java
  val p = cls.constructors.single { it.parameterCount == 0 }.newInstance()
  cls.getDeclaredField("firstName").apply {
    isAccessible = true
    set(p, "Felix")
  }
  cls.getDeclaredField("lastName").apply {
    isAccessible = true
    set(p, "Bytow")
  }

  val p2 = Person2("Felix", "Bytow")

  println("Hello")

  println("$p <=> $p2")

  assert(p == p2)

  println("Bye")
}

open class Config(val host: String, val port: UShort) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Config

    if (host != other.host) return false
    if (port != other.port) return false

    return true
  }

  override fun hashCode(): Int {
    var result = host.hashCode()
    result = 31 * result + port.hashCode()
    return result
  }
}

open class SslConfig(
  host: String,
  port: UShort,
  val certificate: String,
  val key: String
) : Config(host, port) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false

    other as SslConfig

    if (certificate != other.certificate) return false
    if (key != other.key) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + certificate.hashCode()
    result = 31 * result + key.hashCode()
    return result
  }
}

fun initSsl(cfg: SslConfig) {
}

fun initServer(cfg: Config) {
  if (cfg is SslConfig) {
    initSsl(cfg)
  }
}
