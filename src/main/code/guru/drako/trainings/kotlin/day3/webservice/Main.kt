package guru.drako.trainings.kotlin.day3.webservice

import freemarker.cache.ClassTemplateLoader
import guru.drako.trainings.kotlin.day3.webservice.hello.helloModule
import guru.drako.trainings.kotlin.day3.webservice.messages.Message
import guru.drako.trainings.kotlin.day3.webservice.messages.MessageService
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

  val messageService = object : MessageService {
    override fun save(message: Message): Message {
      TODO("Not yet implemented")
    }

    override fun list(limit: Int, offset: Int): List<Message> {
      TODO("Not yet implemented")
    }

    override fun findbyAuthor(author: String, limit: Int, offset: Int): List<Message> {
      TODO("Not yet implemented")
    }

    override fun findById(id: Int): Message? {
      TODO("Not yet implemented")
    }

    override fun deleteById(id: Int) {
      TODO("Not yet implemented")
    }
  }

  helloModule()
  messageModule(messageService)
}

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)
