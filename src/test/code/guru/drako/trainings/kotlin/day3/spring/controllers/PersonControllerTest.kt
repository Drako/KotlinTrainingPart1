package guru.drako.trainings.kotlin.day3.spring.controllers

import com.ninjasquad.springmockk.MockkBean
import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.services.PersonService
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(PersonController::class)
class PersonControllerTest {
  @MockkBean
  private lateinit var service: PersonService

  @Autowired
  private lateinit var mockMvc: MockMvc

  @AfterEach
  fun checkMocks() {
    confirmVerified(service)
  }

  @Test
  fun `create new user`() {
    val newPerson = slot<NewPerson>()
    every { service.newPerson(capture(newPerson)) } answers {
      Person(id = 1, newPerson.captured.firstName, newPerson.captured.lastName)
    }

    val result = mockMvc.perform(
      post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        // language=JSON
        .content("""{"firstName": "Felix", "lastName": "Bytow"}""")
    )
      .andExpect(status().isOk)
      .andReturn()

    result.response.getHeader("Location") shouldBe "/person/1"
    val person = Json.decodeFromString(Person.serializer(), result.response.contentAsString)
    val expected = Person(1, "Felix", "Bytow")

    person shouldBe expected

    verify { service.newPerson(any()) }
  }

  @Test
  fun `delete user successful`() {
    every { service.deletePerson(any()) } returns true

    mockMvc.perform(delete("/person/42"))
      .andExpect(status().isOk)

    verify { service.deletePerson(any()) }
  }

  @Test
  fun `delete user failure`() {
    every { service.deletePerson(any()) } returns false

    mockMvc.perform(delete("/person/42"))
      .andExpect(status().isNotFound)

    verify { service.deletePerson(any()) }
  }
}
