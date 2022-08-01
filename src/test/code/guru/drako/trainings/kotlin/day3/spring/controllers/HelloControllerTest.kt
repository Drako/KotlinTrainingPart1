package guru.drako.trainings.kotlin.day3.spring.controllers

import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(HelloController::class)
@Tag(Day3Test)
class HelloControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  fun `greet the entire world`() {
    val response = mockMvc
      .perform(get("/greet"))
      .andExpect(status().isOk)
      .andReturn()
      .response
      .contentAsString

    // val result = mockMvc.perform(get("/greet"))
    //   .andReturn()
    // result.response.status == 200

    val message = Json.decodeFromString(HelloController.Message.serializer(), response)
    val expected = HelloController.Message(content = "Hello, world!")

    message shouldBe expected
  }

  @Test
  fun `greet Felix`() {
    val response =
      mockMvc.perform(get("/greet?whom=Felix")).andExpect(status().isOk).andReturn().response.contentAsString

    val message = Json.decodeFromString(HelloController.Message.serializer(), response)
    val expected = HelloController.Message(content = "Hello, Felix!")

    message shouldBe expected
  }
}
