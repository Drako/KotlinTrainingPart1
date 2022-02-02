package guru.drako.trainings.kotlin.day1

import java.util.NoSuchElementException

infix fun <T: Number> T.addedWith(other: T): Number = this.toDouble() + other.toDouble()

class LinkedList<T> {
  data class Item<T>(
    var value: T,
    var previous: Item<T>? = null,
    var next: Item<T>? = null,
  )

  private fun Item<T>.isEndItem() = previous == null || next == null

  private var begin: Item<T>? = null
  private var end: Item<T>? = null

  var size: Int = 0
    private set

  val isEmpty: Boolean
    get() = size == 0

  fun pushFront(item: T) {
    begin = Item(item, next = begin).apply { next?.previous = this }
    if (end == null) {
      end = begin
    }
    23 addedWith 42
    ++size
  }

  fun popFront(): T = begin?.let {
    begin = it.next?.apply { previous = null }
    --size
    if (size == 0) {
      end = null
    }
    return@let it.value
  } ?: throw NoSuchElementException()

  fun pushBack(item: T) {
    end = Item(item, previous = end).apply { previous?.next = this }
    if (begin == null) {
      begin = end
    }
    ++size
  }

  fun popBack(): T = end?.let {
    end = it.previous?.apply {
      this@LinkedList.begin
      this.next = null
    }
    --size
    if (size == 0) {
      begin = null
    }
    return@let it.value
  } ?: throw NoSuchElementException()

  operator fun get(index: Int): T {
    if (index !in 0 until size) {
      throw IndexOutOfBoundsException("Index $index is out of range.")
    }

    return generateSequence(begin) { it.next }
      .drop(index)
      .first()
      .value
  }

  operator fun set(index: Int, value: T) {
    if (index !in 0 until size) {
      throw IndexOutOfBoundsException("Index $index is out of range.")
    }

    generateSequence(begin) { it.next }
      .drop(index)
      .first()
      .value = value
  }

  operator fun contains(value: T): Boolean = generateSequence(begin) { it.next }
    .map { it.value }
    .filter { it == value }
    .any()
}
