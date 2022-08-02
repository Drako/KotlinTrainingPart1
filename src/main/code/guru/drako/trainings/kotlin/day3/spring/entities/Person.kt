package guru.drako.trainings.kotlin.day3.spring.entities

import kotlinx.serialization.Serializable
import javax.persistence.*

@Serializable
data class NewPerson(val firstName: String, val lastName: String)

@Serializable
data class UpdatePerson(val firstName: String? = null, val lastName: String? = null)

@Serializable
@Entity
@Table(name = "person")
data class Person(
  @Id
  val id: Int,
  var firstName: String,
  var lastName: String
)

// try not to do this:
// data class Person(var id: Int? = null, var firstName: String? = null, var lastName: String? = null)
