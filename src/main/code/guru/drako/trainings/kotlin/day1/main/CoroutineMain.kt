package guru.drako.trainings.kotlin.day1.main

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.dropWhile
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlin.random.Random

suspend fun typeOut(message: String) {
  for (c in message) {
    print(c)
    delay(Random.nextLong(from = 100L, until = 2000L))
  }
  println()
}

fun CoroutineScope.fib(): ReceiveChannel<Int> = produce<Int> {
  var a = 0
  var b = 1
  while (true) {
    send(a)
    a = b.also { b += a }
  }
}

fun main() {
  val single = newSingleThreadContext("single").asExecutor().asCoroutineDispatcher()
  val pool = newFixedThreadPoolContext(2, "pool").asExecutor().asCoroutineDispatcher()

  runBlocking {
    val chan = Channel<Char>()

    val answer: Deferred<Int> = async(Dispatchers.Default) {
      println("Calculating answer to the question of the life the universe and everything...")
      delay(1000L)
      println("Done.")

      for (c in "Hello\n") {
        delay(10L)
        chan.send(c)
      }
      chan.close()

      return@async 6 * 7
    }

    for (value in chan) {
      print(value)
    }

    val numbers = fib()

    numbers.receiveAsFlow()
      .take(10)
      .onEach { println(it) }
      .collect()

    numbers.cancel()

    println("Calculation started.")

    println(answer.await())

    // typeOut("Hello world!")
  }
}
