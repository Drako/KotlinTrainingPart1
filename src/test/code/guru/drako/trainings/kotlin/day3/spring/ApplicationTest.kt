package guru.drako.trainings.kotlin.day3.spring

import guru.drako.trainings.kotlin.Day3Test
import guru.drako.trainings.kotlin.day3.spring.controllers.HelloController
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Tag(Day3Test)
class ApplicationTest {
  @Autowired
  private lateinit var helloController: HelloController

  @Test
  fun `context loads`() {
    assertTrue(this::helloController.isInitialized)
  }
}
