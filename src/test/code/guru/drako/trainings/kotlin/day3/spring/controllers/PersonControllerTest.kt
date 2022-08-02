package guru.drako.trainings.kotlin.day3.spring.controllers

import com.ninjasquad.springmockk.MockkBean
import guru.drako.trainings.kotlin.SpringTest
import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.services.PersonService
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import javax.servlet.http.HttpServletResponse.SC_OK

@ExtendWith(SpringExtension::class)
@WebMvcTest(PersonController::class)
@TestInstance(Lifecycle.PER_CLASS)
@Tag(SpringTest)
class PersonControllerTest {
  @MockkBean
  private lateinit var service: PersonService

  @Autowired
  private lateinit var controller: PersonController

  @BeforeEach
  fun resetMocks() {
    clearMocks(service)
  }

  @AfterEach
  fun checkMocks() {
    confirmVerified(service)
  }

  @Test
  fun `create new user`() {
    // setup mocks
    val newPerson = slot<NewPerson>()
    every { service.newPerson(capture(newPerson)) } answers {
      // it.invocation.args[0] as NewPerson
      Person(id = 1, newPerson.captured.firstName, newPerson.captured.lastName)
    }

    // call the function to test
    val response = controller.newPerson(NewPerson("Felix", "Bytow"))

    // assert results
    response.statusCode shouldBe HttpStatus.OK
    response.headers.location shouldBe URI.create("/person/1")

    val expected = Person(1, "Felix", "Bytow")
    response.body shouldBe expected

    // verify mocks
    verify(exactly = 1) { service.newPerson(any()) }
  }

  @Test
  fun `delete user successful`() {
    every { service.deletePerson(any()) } returns true

    val response = mockk<HttpServletResponse>()
    every { response.status = SC_OK } just Runs
    controller.deletePerson(42, response)

    verify(exactly = 1) { service.deletePerson(any()) }
    verify(exactly = 1) { response.status = SC_OK }
  }

  @Test
  fun `delete user failure`() {
    every { service.deletePerson(any()) } returns false

    val response = mockk<HttpServletResponse>()
    every { response.status = SC_NOT_FOUND } just Runs
    controller.deletePerson(42, response)

    verify(exactly = 1) { service.deletePerson(any()) }
    verify(exactly = 1) { response.status = SC_NOT_FOUND }
  }
}
