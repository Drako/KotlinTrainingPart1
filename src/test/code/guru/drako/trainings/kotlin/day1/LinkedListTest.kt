package guru.drako.trainings.kotlin.day1

import guru.drako.trainings.kotlin.Day1Test
import io.kotest.matchers.should
import io.kotest.matchers.types.beOfType
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day1Test)
class LinkedListTest {
  companion object {
    @JvmStatic
    fun input(): Stream<Arguments> = Stream.of(
      Arguments.of(10, IllegalArgumentException::class),
      Arguments.of(20, NullPointerException::class),
      Arguments.of(30, NotImplementedError::class),
    )
  }

  fun foo(value: Int) {
    when (value) {
      10 -> {
        throw IllegalArgumentException()
      }
      20 -> {
        throw NullPointerException()
      }
      30 -> {
        throw NotImplementedError()
      }
    }
  }

  @ParameterizedTest
  @MethodSource("input")
  fun `should throw expected exception`(value: Int, expected: KClass<out Throwable>) {
    val ex = assertThrows<Throwable> { foo(value) }
    ex should beOfType(expected)
  }

  @Test
  fun `adding and removing elements to and from the front`() {
    val list = LinkedList<Int>()
    assertTrue(list.isEmpty)

    list.pushFront(23)
    assertEquals(expected = 1, actual = list.size)
    assertEquals(expected = 23, actual = list[0])

    list.pushFront(42)
    assertEquals(expected = 2, actual = list.size)
    assertEquals(expected = 42, actual = list[0])

    list.pushFront(1337)
    assertEquals(expected = 3, actual = list.size)
    assertEquals(expected = 1337, actual = list[0])

    assertTrue(23 in list)
    assertTrue(42 in list)
    assertTrue(1337 in list)
    assertFalse(list.isEmpty)

    assertEquals(expected = 1337, actual = list.popFront())
    assertEquals(expected = 2, actual = list.size)

    assertEquals(expected = 42, actual = list.popFront())
    assertEquals(expected = 1, actual = list.size)

    assertEquals(expected = 23, actual = list.popFront())
    assertEquals(expected = 0, actual = list.size)
    assertTrue(list.isEmpty)

    assertThrows<NoSuchElementException> { list.popFront() }
  }

  @Test
  fun `adding and removing elements to and from the back`() {
    val list = LinkedList<Int>()
    assertTrue(list.isEmpty)

    list.pushBack(23)
    assertEquals(expected = 1, actual = list.size)
    assertEquals(expected = 23, actual = list[0])

    list.pushBack(42)
    assertEquals(expected = 2, actual = list.size)
    assertEquals(expected = 42, actual = list[1])

    list.pushBack(1337)
    assertEquals(expected = 3, actual = list.size)
    assertEquals(expected = 1337, actual = list[2])

    assertTrue(23 in list)
    assertTrue(42 in list)
    assertTrue(1337 in list)
    assertFalse(list.isEmpty)

    assertEquals(expected = 1337, actual = list.popBack())
    assertEquals(expected = 2, actual = list.size)

    assertEquals(expected = 42, actual = list.popBack())
    assertEquals(expected = 1, actual = list.size)

    assertEquals(expected = 23, actual = list.popBack())
    assertEquals(expected = 0, actual = list.size)
    assertTrue(list.isEmpty)

    assertThrows<NoSuchElementException> { list.popBack() }


  }

  @Test
  fun `setting arbitrary values`() {
    val list = LinkedList<Int>()
    repeat(times = 10) {
      list.pushBack(0)
    }

    list[1] = 23
    list[5] = 42
    list[7] = 1337
    assertEquals(expected = 23, actual = list[1])
    assertEquals(expected = 42, actual = list[5])
    assertEquals(expected = 1337, actual = list[7])

    for (badIndex in listOf(-1, 10, 100)) {
      assertThrows<IndexOutOfBoundsException> { list[badIndex] }.also { ex ->
        assertEquals(expected = "Index $badIndex is out of range.", actual = ex.message)
      }

      assertThrows<IndexOutOfBoundsException> { list[badIndex] = 5 }.also { ex ->
        assertEquals(expected = "Index $badIndex is out of range.", actual = ex.message)
      }
    }
  }
}
