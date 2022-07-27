package guru.drako.trainings.kotlin.day1

class LinkedList<T> {
  val size: Int
    get() = TODO("determine size of list")

  val isEmpty: Boolean
    get() = TODO("check if list is empty")

  fun pushFront(item: T) {
    TODO("insert $item at front of list")
  }

  fun popFront(): T = TODO("remove front of list and return it")

  fun pushBack(item: T) {
    TODO("insert $item at end of list")
  }

  fun popBack(): T = TODO("remove end of list and return it")

  operator fun get(index: Int): T = TODO("get item at $index")

  operator fun set(index: Int, value: T) {
    TODO("set item at $index to $value")
  }

  operator fun contains(value: T): Boolean = TODO("check if list contains $value")
}
