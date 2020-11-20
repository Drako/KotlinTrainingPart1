package guru.drako.trainings.kotlin.day3.webservice.hello

import kotlinx.serialization.Serializable

@Serializable
data class Message(
  val author: String,
  val content: String
)
