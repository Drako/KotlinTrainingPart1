package guru.drako.trainings.kotlin.day3.webservice.messages

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.messageModule(messageService: MessageService) {
  routing {
    get("/messages") {
      call.respondText(text = Json.encodeToString(messageService.list()), contentType = ContentType.Application.Json)
    }

    get("/messages/by/author/{author}") {
      val author = call.parameters["author"]
      require(author != null)
      call.respondText(
        text = Json.encodeToString(messageService.findbyAuthor(author)),
        contentType = ContentType.Application.Json
      )
    }

    post("/messages") {
      val newMessage = Message.fromJson(call.receiveText())
      val saved = messageService.save(newMessage)
      call.respondText(
        text = saved.toJson(),
        contentType = ContentType.Application.Json,
        status = HttpStatusCode.Created
      )
    }
  }
}
