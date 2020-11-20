package guru.drako.trainings.kotlin.day3.webservice.messages

import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
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
  @Test
  fun `all messages`() {
    withTestApplication({
      messageModule()
    }) {
      handleRequest(HttpMethod.Get, "/messages").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe Messages.messages
      }
    }
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
      messageModule()
    }) {
      handleRequest(HttpMethod.Get, "/messages/by/author/$author").apply {
        response.contentType() shouldBe ContentType.Application.Json.withParameter("charset", "UTF-8")
        val messages = Json.decodeFromString<List<Message>>(response.content!!)
        messages shouldBe expectedMessages
      }
    }
  }
}
