package guru.drako.trainings.kotlin.day2.gildedrose

import guru.drako.trainings.kotlin.Day2Test
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.exp
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class ShopTest {

  // this function creates an updated item without modifying the original
  private fun Item.update() = copy().also { updatedItem ->
    Shop(items = listOf(updatedItem)).updateQuality()
  }

  data class TestData(
    val displayName: String,
    val input: Item,
    val expected: Item
  )

  companion object {
    private const val LEGENDARY_QUALITY = 80

    @JvmStatic
    fun testCases(): Stream<Arguments> = Stream.of(
      Arguments.of(
        "At the end of each day our system lowers both values for every item",
        Item("test", 10, 10),
        Item("test", 9, 9),
      ),
      Arguments.of(
        "Once the sellIn date has passed, quality degrades twice as fast",
        Item("test", 0, 10),
        Item("test", -1, 8),
      ),
    )
  }

  @ParameterizedTest(name = "{index} => {0}")
  @MethodSource("testCases")
  fun `Item evolves correctly`(displayName: String, input: Item, expected: Item) {
    val updated = input.update()
    updated shouldBe expected
  }

  @Test
  fun `shop modifies given item`() {
    val item = Item("foo", 10, 10)
    val shop = Shop(listOf(item))
    shop.updateQuality()
    item shouldBe Item("foo", 9, 9)
  }

  @TestFactory
  fun `test special behaviors`() =
    listOf(
      TestData(
        displayName = "legendary item Sulfuras does not degrade",
        input = Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 0, quality = LEGENDARY_QUALITY),
        expected = Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 0, quality = LEGENDARY_QUALITY)
      ),
      TestData(
        displayName = "Aged products get better with time",
        input = Item(name = "Aged Brie", sellIn = 2, quality = 0),
        expected = Item(name = "Aged Brie", sellIn = 1, quality = 1)
      ),
      TestData(
        displayName = "Aged products get better even faster after sellIn",
        input = Item(name = "Aged Brie", sellIn = -1, quality = 5),
        expected = Item(name = "Aged Brie", sellIn = -2, quality = 7)
      ),
      TestData(
        displayName = "backstage passes get more valuable approaching concert",
        input = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 15, quality = 20),
        expected = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 14, quality = 21)
      ),
      TestData(
        displayName = "backstage passes get more valuable approaching concert (< 11 days left)",
        input = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 10, quality = 25),
        expected = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 9, quality = 27)
      ),
      TestData(
        displayName = "backstage passes get more valuable approaching concert (< 6 days left)",
        input = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 5, quality = 35),
        expected = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 4, quality = 38)
      ),
      TestData(
        displayName = "backstage passes become worthless after concert",
        input = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 0, quality = 50),
        expected = Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = -1, quality = 0)
      ),
      TestData(
        displayName = "regular items degrade daily",
        input = Item(name = "dummy", sellIn = 10, quality = 10),
        expected = Item(name = "dummy", sellIn = 9, quality = 9)
      ),
      TestData(
        displayName = "regular items degrade twice as fast after sellIn",
        input = Item(name = "dummy", sellIn = -1, quality = 10),
        expected = Item(name = "dummy", sellIn = -2, quality = 8)
      )
    ).map { (displayName, input, expected) ->
      dynamicTest(displayName) {
        assertEquals(
          expected = expected,
          actual = input.update()
        )
      }
    }
}
