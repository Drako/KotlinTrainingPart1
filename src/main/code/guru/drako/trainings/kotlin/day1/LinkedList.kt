package guru.drako.trainings.kotlin.day1

class LinkedList<T> {
  private data class Node<T>(
    var value: T,
    var prev: Node<T>? = null,
    var next: Node<T>? = null
  )

  private var begin: Node<T>? = null
  private var end: Node<T>? = null

  var size: Int = 0
    private set

  val isEmpty: Boolean
    get() = size == 0

  fun pushFront(item: T) {
    ++size

    begin = Node(value = item, next = begin)
      .apply { next?.prev = this }

    if (size == 1) {
      end = begin
    }
  }

  fun popFront(): T = begin?.let { (value, _, next) ->
    --size

    if (size == 0) {
      end = null
    }

    begin = next?.apply { prev = null }
    return@let value
  } ?: throw NoSuchElementException()

  fun pushBack(item: T) {
    ++size

    end = Node(value = item, prev = end)
      .apply { prev?.next = this }

    if (size == 1) {
     begin = end
    }
  }

  fun popBack(): T = end?.let { (value, prev, _) ->
    --size

    if (size == 0) {
      begin = null
    }

    end = prev?.apply { next = null }
    return@let value
  } ?: throw NoSuchElementException()

  operator fun get(index: Int): T = generateSequence(begin) { current -> current.next }
    .drop(if (index < 0) size else index)
    .firstOrNull()
    ?.value
    ?: throw IndexOutOfBoundsException("Index $index is out of range.")

  operator fun set(index: Int, value: T) = generateSequence(begin) { current -> current.next }
    .drop(if (index < 0) size else index)
    .firstOrNull()
    ?.run {
      this.value = value
    }
    ?: throw IndexOutOfBoundsException("Index $index is out of range.")

  operator fun contains(value: T) = generateSequence(begin) { current -> current.next }
    .any { it.value == value }

  /*
  operator fun contains(value: T): Boolean {
    if (isEmpty) {
      return false
    }

    for (idx in 0 until size) {
      if (get(idx) == value) {
        return true
      }
    }

    return false
  }
   */
}
