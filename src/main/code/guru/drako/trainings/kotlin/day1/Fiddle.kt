@file:JvmName("Fiddle")

package guru.drako.trainings.kotlin.day1

data class SslConfig(
  val certificate: String,
  val key: String
)

data class HttpServerConfig(
  val host: String,
  val port: UShort,
  val ssl: SslConfig? = null
)

enum class Protocol {
  HTTP,
  HTTPS
}

fun initSsl(config: SslConfig) {}

fun initServer(config: HttpServerConfig) : Protocol {
  return if (config.ssl != null) {
    initSsl(config.ssl)

    // BAD!
    // initSsl(config.sslCertifacte!!, config.sslKey!!)
    // initSsl(config.sslCertifacte ?: DEFAULT_CERTIFICATE_PATH, config.sslKey ?: "")

    Protocol.HTTPS
  } else {
    Protocol.HTTP
  }
}


class Foo @JvmOverloads constructor(
  message: String,
  message2: String = "",
  answer: Int = 23
) {
  var answer: Int = answer
    get() = field*2
    set(value) { field = value/2 }

  init {

  }

  constructor(n: Int) : this("$n") {

  }

  var size: Int = 0
    private set

  val isEmpty: Boolean
    get() = size == 0

  var message: String
    set(value) { println(value) }
    get() = readln()

  fun say() {
    println(message)
  }
}

// language=Java
val fooJava = """
  public final class Foo {
    private final String message;
  
    public Foo(final String message) {
      this.message = message;
    }
    
    public void say() {
      System.out.println(getMessage());
    }
    
    public String getMessage() {
      return message;
    }
    
    private void setMessage(final String message) {
      this.message = message;
    }
  }
""".trimIndent()


@JvmOverloads
fun increase(n: Int, by: Int = 1) = n + by

private val numbers = mutableListOf<Int>()

fun getNumbers(): List<Int> {
  return numbers
}

fun maybeString(): String? = TODO()
fun main() {
  val foo = Foo("sadsad")
  foo.message = "Enter a message:"
  val entered = foo.message
  foo.message = entered

  val str = maybeString()
  if (!str.isNullOrBlank()) {
    str.chars()
  }

  23.square()

  // BAD! tight coupling to implementation details
  getNumbers() as MutableList<Int>

  val numbersA = mutableListOf<Int>()
  numbersA += 23

  var numbersB = listOf<Int>()
  numbersB += 23

  // BAD!
  var numbersC = mutableListOf<Int>()

  val numbers = listOf(23, 42, 1337)

  for ((index, n) in numbers.withIndex()) {
    println("$index => $n")
  }
}
