package guru.drako.trainings.kotlin.day1

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
    // set begin to point to second item
    // if second item exists (list is not empty after pop), remove link to old begin
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
    var node = begin
    var i = 0
    while (node != null && i < index) {
      node = node.next
      ++i
    }
    if (i == index && node != null) {
      return node.value
    } else {
      throw NoSuchElementException()
    }
    */

    // return generateSequence(head.next as? NodeWithValue<T>) { it.next as? NodeWithValue<T> }
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
    .any { it.value == value }
}
