package guru.drako.trainings.kotlin.day3.webservice.messages

import kotlinx.serialization.Serializable

@Serializable
data class Message(
  val author: String,
  val content: String
)