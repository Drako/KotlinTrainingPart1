package guru.drako.trainings.kotlin.day3.webservice.hello

import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day3Test)
class HelloTest {
  @Test
  fun `start page`() {
    // language=HTML
    val expectedStartPage = """<!DOCTYPE html>
       |<html>
       |  <head>
       |    <title>Hello world!</title>
       |  </head>
       |  <body>
       |    <p>Hello world!</p>
       |  </body>
       |</html>
       |""".trimMargin()

    withTestApplication({
      helloModule()
    }) {
      handleRequest(HttpMethod.Get, "/").apply {
        response.contentType() shouldBe ContentType.Text.Html.withParameter("charset", "UTF-8")
        response.content shouldBe expectedStartPage
      }
    }
  }

  // TODO: add parameterized test for /greet/{who?}
}
