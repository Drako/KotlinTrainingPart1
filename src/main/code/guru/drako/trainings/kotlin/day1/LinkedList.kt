package guru.drako.trainings.kotlin.day1

import java.util.NoSuchElementException

class LinkedList<T> {
  data class Item<T>(
    var value: T,
    var previous: Item<T>? = null,
    var next: Item<T>? = null,
  )

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
    end = it.previous?.apply { next = null }
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

    /*
    listOf(1, 2, 3, 4, 5, 6, 7,8, 9, 10)
      .asSequence()
      .filter { it % 2 == 0 }
      .map { "$it" }
      .toList()

    val result = mutableListOf<Int>()
    for (n in 1..10) {
      if (n % 2 == 0) {
        result.add(n)
      }
    }
    */

    return generateSequence(begin) { it.next }
      .drop(index)
      .first()
      .value
  }

  operator fun set(index: Int, value: T) {
    if (index !in 0 until size) {
      throw IndexOutOfBoundsException("Index $index is out of range.")
    }

    val mapping = mapOf(
      "pi" to 3.1415926,
      "leet" to 13.37,
      "answer" to 42.0
    )

    val keys = listOf("pi", "leet", "e", "i")
    val values = keys.asSequence()
      .mapNotNull { key -> mapping[key]?.let { key to it } }
      .toMap()


    generateSequence(begin) { it.next }
      .drop(index)
      .first()
      .value = value
  }

  operator fun contains(value: T): Boolean = generateSequence(begin) { it.next }
    .any { it.value == value }
}
