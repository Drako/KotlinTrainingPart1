package guru.drako.trainings.kotlin.day2.gildedrose

import guru.drako.trainings.kotlin.Day2Test
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class ShopTest {
  companion object {
    private const val LEGENDARY_QUALITY = 80
  }

  private fun Item.update() = copy().also { updatedItem ->
    Shop(items = listOf(updatedItem)).updateQuality()
  }

  private data class TestData(
    val displayName: String,
    val input: Item,
    val expected: Item
  )

  @TestFactory
  fun `test special behaviors`() =
    sequenceOf(
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
    }.iterator()
}
