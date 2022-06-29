package guru.drako.trainings.kotlin.day2

import kotlin.coroutines.*

@RestrictsSuspension
class NoSuspension

sealed class ExecutionResult<T>

class ExecutionFailure(val exception: Throwable) : ExecutionResult<Any?>()

class ExecutionSuccess<T>(val result: T) : ExecutionResult<T>()

fun <T> justRun(block: suspend NoSuspension.() -> T): T {
  var coroutineResult: ExecutionResult<T>? = null

  val coroutine = block.createCoroutine(NoSuspension(), object : Continuation<T> {
    override val context: CoroutineContext
      get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<T>) {
      coroutineResult = result.getOrNull()?.let { ExecutionSuccess(it) }
        ?: result.exceptionOrNull()?.let {
          @Suppress("UNCHECKED_CAST")
          ExecutionFailure(it) as ExecutionResult<T>
        }
    }
  })

  coroutine.resume(Unit)

  return when (val result = coroutineResult) {
    is ExecutionSuccess<T> -> result.result
    is ExecutionFailure -> throw result.exception
    null -> throw IllegalStateException()
  }
}

suspend fun NoSuspension.answer(): Int {
  return 6 * 7
}

fun main() {
  val answer = justRun { answer() }
  println("The answer is $answer")
}
