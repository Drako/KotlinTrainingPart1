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

  // TODO: add parameterized test for /messages/by/author/{author}
}
