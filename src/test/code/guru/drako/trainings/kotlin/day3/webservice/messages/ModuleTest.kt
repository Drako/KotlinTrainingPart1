package guru.drako.trainings.kotlin.day3.webservice.messages

import guru.drako.trainings.kotlin.Day3Test
import guru.drako.trainings.kotlin.day3.webservice.DatabaseFactory
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day3Test)
class ModuleTest {
  private val messageService = mockk<MessageService>()

  private val actualMessageService = ExposedMessageService()

  @BeforeEach
  fun setUp() {
    clearMocks(messageService)
  }

  @Test
  fun `all messages`() {
    withTestApplication({
      DatabaseFactory.testInit()

      messageModule(actualMessageService)
    }) {
      handleRequest(HttpMethod.Get, "/messages").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe runBlocking { actualMessageService.list() }
      }
    }
  }

  @Test
  fun `posting new message`() {
    coEvery { messageService.save(any()) }.answers { call ->
      (call.invocation.args[0] as Message).copy(id = 1)
    }

    val msg = Message(author = "Buxbunny", content = "Das Publikum war heute wundervoll")

    withTestApplication({
      messageModule(messageService)
    }) {
      handleRequest(HttpMethod.Post, "/messages") {
        setBody(msg.toJson())
        addHeader("Content-Type", "${ContentType.Application.Json}")
      }.apply {
        response.status() shouldBe HttpStatusCode.Created
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val saved = Json.decodeFromString<Message>(response.content!!)
        saved shouldBe msg.copy(id = 1)
      }
    }

    coVerify { messageService.save(any()) }
  }

  @ParameterizedTest(name = "{index} => Author: {0}")
  @ValueSource(strings = ["Felix", "William", "Vlad"])
  fun `messages by author`(author: String) {
    withTestApplication({
      DatabaseFactory.testInit()

      messageModule(actualMessageService)
    }) {
      handleRequest(HttpMethod.Get, "/messages/by/author/$author").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe runBlocking { actualMessageService.findbyAuthor(author) }
      }
    }
  }
}
