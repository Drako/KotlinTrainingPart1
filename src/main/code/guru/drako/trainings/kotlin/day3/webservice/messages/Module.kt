package guru.drako.trainings.kotlin.day3.webservice.messages

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.messageModule() {
  routing {
    get("/messages") {
      call.respondText(text = Json.encodeToString(Messages.messages), contentType = ContentType.Application.Json)
    }

    get("/messages/by/author/{author}") {
      val author = call.parameters["author"]
      call.respondText(
        text = Json.encodeToString(Messages.messages.filter { it.author == author }),
        contentType = ContentType.Application.Json
      )
    }
  }
}
