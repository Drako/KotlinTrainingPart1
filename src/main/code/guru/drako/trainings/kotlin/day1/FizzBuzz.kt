package guru.drako.trainings.kotlin.day1

import kotlin.random.Random

// Java: [nichts] -> package private (jeder im gleichen Package kann sehen)
// Java: protected -> package protected (jeder im gleichen Package kann sehen, und Kind-Klassen der Klasse)

// private -> nur Klasse selber kann sehen
// [nichts] -> public -> jeder kann sehen
// protected -> protected (C++ Style) -> nur Klasse selber und Kind-Klassen können sehen
// internal -> public, aber nur für das aktuelle Modul

private fun fizz(n: Int) = if (n % 3 == 0) "Fizz" else ""
private fun buzz(n: Int) = if (n % 5 == 0) "Buzz" else ""

fun fizzBuzz(n: Int) = (fizz(n) + buzz(n)).also {
  println(it)
}

fun Int.square() = this * this

class InvalidConfigException(
  message: String? = null,
  cause: Throwable? = null
) : RuntimeException(message, cause)

data class ConfigBuilder(
  var numThreads: Int? = null,
  var user: String? = null,
  var group: String? = null
) {
  fun build() = Config(
    numThreads = numThreads ?: throw InvalidConfigException("numThreads missing"),
    user = user ?: throw InvalidConfigException("user missing"),
    group = group ?: throw InvalidConfigException("group missing")
  )
}

data class Config(
  val numThreads: Int,
  val user: String,
  val group: String
)

fun config(configurator: ConfigBuilder.() -> Unit): Config {
  val builder = ConfigBuilder()
  builder.configurator()
  return builder.build()
}

fun main() {
  // let ->  Wert als Parameter -> gibt internes Ergebnis zurück (map für ein Element)
  // also ->  Wert als Parameter -> gibt Receiver zurück
  // apply -> Wert als Receiver -> gibt Receiver zurück
  // run/with -> Wert als Receiver -> gibt internes Ergebnis zurück

  val cfg = ConfigBuilder().apply {
    numThreads = 10
    if (Random.nextBoolean()) {
      numThreads = 1
    }
    user = "www-data"
    group = "www-data"
  }.build()

  with(ConfigBuilder()) {
    numThreads = 10
    if (Random.nextBoolean()) {
      numThreads = 1
    }
    user = "www-data"
    group = "www-data"
    this
  }.build()
}

fun increment(numbers: List<Int>): List<Int> {
  return numbers.map { it + 1 }
}
