package guru.drako.trainings.kotlin.day2.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val numbers = listOf(1, 2, 3)

      args.toList()
      args.asList()

      withArgs(args.asList())
      SpringApplication.run(Application::class.java, *args)
    }

    fun withArgs(vararg args: String) {

    }

    fun withArgs(args: List<String>) {

    }
  }
}
