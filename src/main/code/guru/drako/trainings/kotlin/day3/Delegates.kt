package guru.drako.trainings.kotlin.day3

interface Printer {
  fun println(msg: String)
}

class ConsolePrinter : Printer {
  override fun println(msg: String) {
    System.out.println(msg)
  }
}

class Widget(private val printer : Printer) : Printer by printer {
  // generated:
  /*
    override fun println(msg: String) {
      printer.println(msg)
    }
   */
}


class Config(content: MutableMap<String, Any?>) {
  var host: String by content
  var port: UShort by content
}

fun main() {
  val p = ConsolePrinter()
  val w = Widget(p)
  w.println("Hello world!")

  val cfg = Config(mutableMapOf("host" to "localhost"))
  cfg.port = (80).toUShort()
  println(cfg.host + " - " + cfg.port)
}
