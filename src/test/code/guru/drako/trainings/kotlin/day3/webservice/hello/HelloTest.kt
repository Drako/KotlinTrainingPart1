package guru.drako.trainings.kotlin.day3.webservice.hello

import freemarker.cache.ClassTemplateLoader
import guru.drako.trainings.kotlin.Day3Test
import io.kotest.matchers.shouldBe
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

  @ParameterizedTest(name = "{index} => Greeting {0}")
  @ValueSource(strings = ["Felix", "Marcus", ""])
  fun `greeting someone`(target: String) {
    withTestApplication({
      install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
      }
      helloModule()
    }) {
      handleRequest(HttpMethod.Get, "/greet/$target").apply {
        response.contentType() shouldBe ContentType.Text.Html.withParameter("charset", "UTF-8")
        response.content shouldBe target.ifEmpty { "world" }
      }
    }
  }
}
