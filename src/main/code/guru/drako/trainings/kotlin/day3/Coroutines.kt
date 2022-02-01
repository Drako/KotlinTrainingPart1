package guru.drako.trainings.kotlin.day3

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import java.time.LocalTime
import java.util.function.Predicate
import kotlin.coroutines.*

enum class ExecutionState {
  Running,
  Done
}

// Iterator<T> -> hasNext() und next()
// SequenceBuilderScope<T> -> yield() und yieldAll()
@RestrictsSuspension
interface SequenceBuilderScope<T> {
  suspend fun yield(value: T)
  suspend fun yieldAll(vararg values: T)
}

suspend fun <T> SequenceBuilderScope<T>.foo(values: Array<T>) {
  // spread operator for passing arrays to vararg functions
  yieldAll(*values)
}

class SequenceBuilder<T> : Iterator<T>, SequenceBuilderScope<T> {
  override fun hasNext(): Boolean {
    TODO("Not yet implemented")
  }

  override fun next(): T {
    TODO("Not yet implemented")
  }

  override suspend fun yield(value: T) {
    TODO("Not yet implemented")
  }

  override suspend fun yieldAll(vararg values: T) {
    TODO("Not yet implemented")
  }

}

@RestrictsSuspension
interface TrivialScope {
  suspend fun greet(whom: String)

  suspend fun delay(milliseconds: Long)
}

typealias Action = () -> Unit
typealias Condition = () -> Boolean

class TrivialContext : TrivialScope {
  lateinit var next: Continuation<Unit>
  val pendingActions = mutableListOf<Action>()
  var state = ExecutionState.Running
  var canContinue: Condition = this::alwaysContinue

  private fun alwaysContinue() = true

  override suspend fun greet(whom: String) {
    suspendCoroutine<Unit> {
      next = it
      pendingActions.add { println("Hello, $whom!") }
    }
  }

  override suspend fun delay(milliseconds: Long) {
    val goal = LocalTime.now().plusNanos(milliseconds * 1000000L)
    suspendCoroutine<Unit> {
      next = it
      canContinue = {
        LocalTime.now().isAfter(goal)
      }
    }
    canContinue = this::alwaysContinue
  }
}

fun trivial(block: suspend TrivialScope.() -> Unit) {
  val context = TrivialContext()
  context.next = block.createCoroutine(context, Continuation(EmptyCoroutineContext) { result ->
    context.state = ExecutionState.Done
    println("Coroutine finished: $result")
  })
  while (context.state == ExecutionState.Running) {
    if (context.canContinue()) {
      context.next.resume(Unit)
    }
    for (action in context.pendingActions) {
      action()
    }
    context.pendingActions.clear()
  }
}

fun fibonacci() = sequence {
  var a = 0
  var b = 1
  while (true) {
    yield(a)
    a = b.also { b += a }
  }
}

fun CoroutineScope.ints(): ReceiveChannel<Int> = produce {
  var n = 0
  while (true) {
    delay(200L)
    send(n++)
  }
}

suspend fun TrivialScope.hello() {
  greet("Felix")
  delay(1500L)
  greet("Bytow")
}

fun main() {
  iterator { yield(23) }

  fibonacci().take(10).toList()

  trivial {
    hello()
  }

  runBlocking(Dispatchers.Default) {
    val values = mutableListOf<Int>()
    ints().receiveAsFlow().take(10).toList(values)
    println(values)

    val result = async(Dispatchers.IO) {
      repeat(10) {
        println("Thread ${Thread.currentThread().id}")
        delay(500L)
      }
      return@async 42
    }
    launch {
      repeat(5) {
        println("Thread ${Thread.currentThread().id}")
        delay(1000L)
      }
    }
    println("The answer is ${result.await()}")
  }
}
