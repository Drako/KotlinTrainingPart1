package guru.drako.trainings.kotlin.day3.spring.controllers

import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.entities.UpdatePerson
import guru.drako.trainings.kotlin.day3.spring.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import javax.servlet.http.HttpServletResponse.SC_OK

@RestController
@RequestMapping("person")
class PersonController @Autowired constructor(
  val personService: PersonService
) {
  @PostMapping("/", produces = ["application/json"])
  fun newPerson(@RequestBody person: NewPerson): ResponseEntity<Person> {
    val entity = personService.newPerson(person)
    return ResponseEntity.ok().headers(HttpHeaders().apply {
      location = URI.create("/person/${entity.id}")
    }).body(entity)
  }

  @DeleteMapping("/{id}")
  fun deletePerson(@PathVariable id: Int, response: HttpServletResponse) {
    if (personService.deletePerson(id)) {
      response.status = SC_OK
    } else {
      response.status = SC_NOT_FOUND
    }
  }

  @PutMapping("/{id}", produces = ["application/json"])
  fun updatePerson(@PathVariable id: Int, @RequestBody update: UpdatePerson): ResponseEntity<Person> {
    return personService.updatePerson(id, update)
      ?.let { ResponseEntity.ok(it) }
      ?: ResponseEntity.notFound().build()
  }

  @GetMapping("/{id}", produces = ["application/json"])
  fun getPerson(@PathVariable id: Int): ResponseEntity<Person> {
    return personService.getPerson(id)
      ?.let { ResponseEntity.ok(it) }
      ?: ResponseEntity.notFound().build()
  }

  @GetMapping("/", produces = ["application/json"])
  @ResponseBody
  fun getPeople(
    @RequestParam(required = false, defaultValue = "0") offset: Int,
    @RequestParam(required = false, defaultValue = "10") limit: Int
  ): List<Person> {
    return personService.getPeople(offset, limit)
  }
}
