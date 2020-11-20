package guru.drako.trainings.kotlin.day3.webservice.hello

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.helloModule() {
  install(DefaultHeaders)
  install(CallLogging)
  routing {
    get("/") {
      call.respondHtml {
        head {
          title("Hello world!")
        }

        body {
          p {
            +"Hello world!"
          }
        }
      }
    }

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

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)
