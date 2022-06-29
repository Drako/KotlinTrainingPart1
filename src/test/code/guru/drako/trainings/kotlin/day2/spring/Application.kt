package guru.drako.trainings.kotlin.day2.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(Application::class.java, *args)
    }
  }
}
