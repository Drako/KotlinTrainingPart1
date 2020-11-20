package guru.drako.trainings.kotlin.day3.webservice

import freemarker.cache.ClassTemplateLoader
import guru.drako.trainings.kotlin.day3.webservice.hello.helloModule
import guru.drako.trainings.kotlin.day3.webservice.messages.messageModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*

fun Application.mainModule() {
  install(DefaultHeaders)
  install(CallLogging)
  install(FreeMarker) {
    templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
  }

  helloModule()
  messageModule()
}

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)
