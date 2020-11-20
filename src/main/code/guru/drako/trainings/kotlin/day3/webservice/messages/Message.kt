package guru.drako.trainings.kotlin.day3.webservice.messages

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.Json.Default.encodeToString

@Serializable
data class Message(
  val id: Int? = null,
  val author: String,
  val content: String
) {
  companion object {
    fun fromJson(json: String) = decodeFromString(serializer(), json)
  }

  fun toJson(): String = encodeToString(serializer(), this)
}
