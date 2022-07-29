package guru.drako.trainings.kotlin.day2

// Symbol	I	V	 X	 L	  C	  D	   M
// Value	1	5	10	50	100	500	1000

// I -   1 (can appear up to 3 times)
// IV -  4 (can appear only once)
// V -   5 (can appear only once)
// IX -  9 (can appear only once)
// X -  10 (can appear up to 3 times)
// XL - 40 (can appear only once)
// L -  50 (can appear only once)
// XC - 90 (can appear only once)
// C - 100 (can appear up to 3 times)

// IM is NOT 999

// I - 1
// II - 2
// III - 3
// IV - 4
// V - 5
// VI - 6
// VII - 7
// VIII - 8
// IX - 9
// X - 10
// XI - 11

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

  private data class ConversionState<Target, Source>(
    val target: Target,
    val source: Source
  )

  private val romanToArabicMapping: Map<String, Int> =
    (1..3999).associateBy { arabic ->
      arabic2roman(arabic)
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
    return mapping.fold(ConversionState(target = "", source = arabic))
    { (target, source), (r, a) ->
      if (source == 0) {
        return target
      }
      ConversionState(target = target + r * (source / a), source = source % a)
    }.target
  }

  private operator fun String.times(factor: Int) = repeat(factor)

  // target = EMPTY (0)
  // for ((a, b) in mapping) {
  // if (source != EMPTY ("")) {
  //   factor = source "XIX" / a "X"
  //   source -= factor * a (removePrefix)
  //   target += factor * b (int multiplication)
  // } else {
  //   break
  // }
  // }
}
