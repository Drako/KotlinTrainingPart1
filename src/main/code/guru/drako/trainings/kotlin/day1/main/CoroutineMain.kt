package guru.drako.trainings.kotlin.day1.main

import kotlinx.coroutines.delay
import kotlin.random.Random

suspend fun typeOut(message: String) {
  for (c in message) {
    print(c)
    delay(Random.nextLong(from = 100L, until = 2000L))
  }
  println()
}

suspend fun main() {
  typeOut("Hello world!")
}
