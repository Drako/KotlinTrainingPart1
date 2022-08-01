package guru.drako.trainings.kotlin.day3.spring.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
  data class Message(val content: String)

  @GetMapping("/greet")
  @ResponseBody
  fun getMessage(@RequestParam(required = false) whom: String?): Message {
    return Message("Hello, ${whom ?: "world"}!")
  }
}
