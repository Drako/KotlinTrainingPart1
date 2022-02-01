package guru.drako.trainings.kotlin.day2.gildedrose

class Shop(val items: List<Item>) {
  companion object {
    private const val MIN_QUALITY = 0
    private const val MAX_QUALITY = 50
  }

  private inline fun <T> Sequence<T>.forEachWith(action: T.() -> Unit) = forEach { it.action() }

  private fun Item.qualityModifier(): Int = when {
    isBackstagePass() -> when {
      sellIn <= 0 -> -MAX_QUALITY
      sellIn <= 5 -> 3
      sellIn <= 10 -> 2
      else -> 1
    }
    isAged() -> if (sellIn <= 0) 2 else 1
    isConjured() -> if (sellIn <= 0) -4 else -2
    else -> if (sellIn <= 0) -2 else -1
  }

  fun updateQuality() = items.asSequence()
    .filterNot { it.isLegendary() }
    .forEachWith {
      quality = (quality + qualityModifier()).coerceIn(MIN_QUALITY, MAX_QUALITY)
      --sellIn
    }
}
