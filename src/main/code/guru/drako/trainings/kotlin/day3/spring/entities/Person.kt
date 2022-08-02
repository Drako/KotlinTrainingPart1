package guru.drako.trainings.kotlin.day3.spring.entities

import kotlinx.serialization.Serializable
import javax.persistence.*

// purely input api
@Serializable
data class NewPerson(val firstName: String, val lastName: String)

// purely input api, but only an update-description, so no direct conversion to Person
@Serializable
data class UpdatePerson(val firstName: String? = null, val lastName: String? = null)

// output api
@Serializable
data class PersonResponse(val id: Int?, val firstName: String, val lastName: String) {
  companion object {
    fun fromPerson(person: Person) =
      PersonResponse(id = person.id, firstName = person.firstName, lastName = person.lastName)
  }
}

data class Person(val id: Int, val firstName: String, val lastName: String)

// in/out for persistence
@Entity
@Table(name = "person")
class PersonEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  val id: Int? = null,
  var firstName: String,
  var lastName: String
) {
  fun merge(update: UpdatePerson) = Person(
    id = id ?: throw IllegalStateException(),
    firstName = update.firstName ?: firstName,
    lastName = update.lastName ?: lastName
  )

  fun toPerson() = Person(id ?: throw IllegalStateException(), firstName, lastName)

  companion object {
    fun fromPerson(person: Person) = PersonEntity(person.id, person.firstName, person.lastName)
  }
}

// try not to do this:
// data class Person(var id: Int? = null, var firstName: String? = null, var lastName: String? = null)
