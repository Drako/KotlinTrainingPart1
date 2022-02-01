package guru.drako.trainings.kotlin.day2.gildedrose

private val LEGENDARY_ITEMS = setOf("Sulfuras, Hand of Ragnaros")

fun Item.isLegendary(): Boolean = name in LEGENDARY_ITEMS

fun Item.isBackstagePass(): Boolean = name.startsWith(prefix = "backstage passes", ignoreCase = true)

fun Item.isAged(): Boolean = name.startsWith(prefix = "aged", ignoreCase = true)

fun Item.isConjured(): Boolean = name.startsWith(prefix = "conjured", ignoreCase = true)
