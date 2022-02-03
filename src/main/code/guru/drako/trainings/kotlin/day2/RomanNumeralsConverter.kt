package guru.drako.trainings.kotlin.day2

// dokka
class RomanNumeralsConverter {
  private val mapping: List<Pair<String, Int>> = listOf(
    "M" to 1000,
    "CM" to 900,
    "D" to 500,
    "CD" to 400,
    "C" to 100,
    "XC" to 90,
    "L" to 50,
    "XL" to 40,
    "X" to 10,
    "IX" to 9,
    "V" to 5,
    "IV" to 4,
    "I" to 1
  )

  private val romanToArabicMapping: Map<String, Int> =
    (1..3999).associateBy { arabic ->
      arabic2roman(arabic)
    }

  private inline fun <T: Throwable> `(╯°□°）╯︵`(block: () -> T): Nothing {
    throw block()
  }

  private data class ConversionState<Target, Source>(val target: Target, val source: Source)

  private fun String.prefixCount(prefix: String): Int = when {
    prefix.length == 1 -> asSequence()
      .takeWhile { it == prefix[0] }
      .take(3)
      .count()
    startsWith(prefix) -> 1
    else -> 0
  }

  /**
   * Converts a roman numeral [String] into a normal [Int].
   *
   * @param roman A [String] containing a valid roman numeral.
   * @return The numeric value of the given roman numeral.
   *
   * @throws NumberFormatException if the parameter is not a valid roman numeral.
   */
  fun roman2arabic(roman: String): Int {
    return romanToArabicMapping[roman]
      ?: `(╯°□°）╯︵` { NumberFormatException("$roman is not a valid roman numeral") }
    /*
    val result = mapping.fold(ConversionState(target = 0, source = roman)) { (target, source), (r, a) ->
      val factor = source.prefixCount(r)
      ConversionState(target = target + a * factor, source = source.removePrefix(r.repeat(factor)))
    }
    if (result.source.isNotEmpty() || result.target == 0) {
      `(╯°□°）╯︵` { NumberFormatException("$roman is not a valid roman numeral") }
    }
    return result.target
    */
  }

  /**
   * Converts a normal [Int] into a [String] containing a roman numeral.
   *
   * @param arabic The number to be converted.
   * @return The number as a roman numeral.
   *
   * @throws IllegalArgumentException if the parameter is not in the range 1..3999.
   */
  fun arabic2roman(arabic: Int): String {
    require(arabic in 1..3999)
    return mapping.fold(ConversionState(target = "", source = arabic)) { (target, source), (r, a) ->
      if (source == 0) {
        return target
      }
      ConversionState(target = target + r.repeat(source / a), source = source % a)
    }.target
  }
}
