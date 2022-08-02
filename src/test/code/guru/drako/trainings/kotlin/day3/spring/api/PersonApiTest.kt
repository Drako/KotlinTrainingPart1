package guru.drako.trainings.kotlin.day3.spring.api

import guru.drako.trainings.kotlin.SpringTest
import guru.drako.trainings.kotlin.day3.spring.controllers.PersonController
import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.entities.PersonResponse
import guru.drako.trainings.kotlin.day3.spring.repositories.PersonRepository
import guru.drako.trainings.kotlin.day3.spring.services.PersonService
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Tag(SpringTest)
class PersonApiTest {
  @Autowired
  private lateinit var service: PersonService

  @Autowired
  private lateinit var mockMvc: MockMvc

  @BeforeEach
  fun preparePeople() {
    service.clear()
    // 11 people
    val testPeople = listOf(
      NewPerson(firstName = "Tony", lastName = "Stark"),
      NewPerson(firstName = "Steve", lastName = "Rogers"),
      NewPerson(firstName = "Peter", lastName = "Parker"),
      NewPerson(firstName = "Steven", lastName = "Strange"),
      NewPerson(firstName = "Wanda", lastName = "Maximoff"),
      NewPerson(firstName = "Brucer", lastName = "Banner"),
      NewPerson(firstName = "Hank", lastName = "Pym"),
      NewPerson(firstName = "Scott", lastName = "Lang"),
      NewPerson(firstName = "Natasha", lastName = "Romanoff"),
      NewPerson(firstName = "Clint", lastName = "Barton"),
      NewPerson(firstName = "Thor", lastName = "Odinson"),
    )
    for (person in testPeople) {
      service.newPerson(person)
    }
  }

  @Test
  fun `create new user`() {
    val result = mockMvc.perform(
      MockMvcRequestBuilders.post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        // language=JSON
        .content("""{"firstName": "Felix", "lastName": "Bytow"}""")
    )
      .andExpect(status().isOk)
      .andReturn()

    val person = Json.decodeFromString(PersonResponse.serializer(), result.response.contentAsString)

    result.response.getHeader("Location") shouldBe "/person/${person.id}"
    val expected = PersonResponse(person.id, "Felix", "Bytow")

    person shouldBe expected
  }

  @Test
  fun `delete user successful`() {
    mockMvc.perform(get("/person/10"))
      .andExpect(status().isOk)

    mockMvc.perform(delete("/person/10"))
      .andExpect(status().isOk)

    mockMvc.perform(get("/person/10"))
      .andExpect(status().isNotFound)
  }

  @Test
  fun `delete user failure`() {
    mockMvc.perform(delete("/person/42"))
      .andExpect(status().isNotFound)
  }

  @Test
  fun `update non existent person`() {
    val response = mockMvc.perform(
      put("/person/1337")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        // language=JSON
        .content("""{"firstName": "Marcus"}""")
    ).andExpect(status().isNotFound)
      .andReturn().response

    response.errorMessage shouldBe "Person#1337 does not exist."
  }
}
