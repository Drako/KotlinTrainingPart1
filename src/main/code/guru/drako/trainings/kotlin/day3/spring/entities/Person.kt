package guru.drako.trainings.kotlin.day3.spring.entities

import kotlinx.serialization.Serializable

@Serializable
data class NewPerson(val firstName: String, val lastName: String)

@Serializable
data class UpdatePerson(val firstName: String? = null, val lastName: String? = null)

@Serializable
data class Person(val id: Int, var firstName: String, var lastName: String)
