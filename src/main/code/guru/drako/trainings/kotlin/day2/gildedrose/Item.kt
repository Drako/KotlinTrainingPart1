package guru.drako.trainings.kotlin.day2.gildedrose

data class Item(
  val name: String,
  var sellIn: Int,
  var quality: Int
) {
  // language=json
  override fun toString() = """{ "name": "$name", "sellIn": $sellIn, "quality": $quality }"""
}
