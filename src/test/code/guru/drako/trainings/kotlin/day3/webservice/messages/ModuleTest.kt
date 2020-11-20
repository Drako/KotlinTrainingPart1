package guru.drako.trainings.kotlin.day3.webservice.messages

import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day3Test)
class ModuleTest {
  private val messageService = mockk<MessageService>()

  @BeforeEach
  fun setUp() {
    clearMocks(messageService)
  }

  @Test
  fun `all messages`() {
    withTestApplication({
      messageModule(messageService)
    }) {
      handleRequest(HttpMethod.Get, "/messages").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe Messages.messages
      }
    }
  }

  @Test
  fun `posting new message`() {
    every { messageService.save(any()) }.answers { call ->
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

    verify { messageService.save(any()) }
  }

  companion object {
    @Suppress("unused")
    @JvmStatic
    fun testMessagesByAuthor(): Stream<Arguments> = Stream.of(
      Arguments.of("Bill Gates", listOf<Message>()),
      Arguments.of("Felix Bytow", listOf(
        Message(author = "Felix Bytow", content = "Hello world!"),
        Message(author = "Felix Bytow", content = "How art thou today?")
      )),
      Arguments.of("Vlad the Impaler", listOf(
        Message(author = "Vlad the Impaler", content = "как тебя зовут?")
      ))
    )
  }

  @ParameterizedTest(name = "{index} => Author: {0}, Expected messages: {1}")
  @MethodSource("testMessagesByAuthor")
  fun `messages by author`(author: String, expectedMessages: List<Message>) {
    withTestApplication({
      messageModule(messageService)
    }) {
      handleRequest(HttpMethod.Get, "/messages/by/author/$author").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe expectedMessages
      }
    }
  }
}
