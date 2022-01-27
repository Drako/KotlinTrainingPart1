package guru.drako.trainings.kotlin.day2

interface X
open class Y

// extends -> inherit from class
// implements -> implement interface

object Foo : Y(), X {
  fun foo() {
    val x: Runnable = Runnable {  }

  }

  val ANSWER = 42

  const val LEET = 1337
}

class C {
  companion object : Runnable {
    fun x() {}
    override fun run() {
      TODO("Not yet implemented")
    }
  }

  fun y() {
    x()
    sequenceOf<Char>().joinToString()
  }

  fun x() {
    Companion.x()
  }
}

val x = Foo.ANSWER
