package guru.drako.trainings.kotlin.day3.webservice.hello

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

fun Application.helloModule() {
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

    get("/greet/{who?}") {
      val name = call.parameters["who"] ?: "world"
      call.respond(FreeMarkerContent("hello.ftl", mapOf("name" to name)))
    }
  }
}
