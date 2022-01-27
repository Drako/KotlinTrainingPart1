package guru.drako.trainings.kotlin.day2

import guru.drako.trainings.kotlin.Day2Test
import guru.drako.trainings.kotlin.day2.RomanNumeralsConverter.arabic2roman
import guru.drako.trainings.kotlin.day2.RomanNumeralsConverter.roman2arabic
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.ints
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class RomanNumeralsConverterTest {
  @ParameterizedTest(name = "{index} ==> {1} should be {0}")
  @CsvFileSource(resources = ["RomanNumerals.csv"])
  fun `Conversion Roman to Arabic`(arabic: Int, roman: String) {
    roman2arabic(roman) shouldBeExactly arabic
  }

  @ParameterizedTest(name = "{index} ==> {0} should throw")
  @ValueSource(strings = ["N", "ABC", "IL", "CDC"])
  fun `Roman to Arabic should fail if number is not a roman numeral`(n: String) {
    val ex = assertThrows<IllegalArgumentException> {
      roman2arabic(n)
    }
    // ex.message shouldBe "foo"
  }

  @ParameterizedTest(name = "{index} ==> {0} should be {1}")
  @CsvFileSource(resources = ["RomanNumerals.csv"])
  fun `Conversion Arabic to Roman`(arabic: Int, roman: String) {
    arabic2roman(arabic) shouldBe roman
  }

  @ParameterizedTest(name = "{index} ==> {0} should throw")
  @ValueSource(ints = [0, 4000])
  fun `Arabic to Roman should fail if number out of bounds`(n: Int) {
    assertThrows<IllegalArgumentException> {
      arabic2roman(n)
    }
  }

  @Test
  fun `Back and forth should work`(): Unit = runBlocking {
    checkAll(Exhaustive.ints(1..3999)) { n ->
      roman2arabic(arabic2roman(n)) shouldBeExactly n
    }
  }
}
