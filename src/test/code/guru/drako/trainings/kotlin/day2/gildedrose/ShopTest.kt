package guru.drako.trainings.kotlin.day2.gildedrose

import guru.drako.trainings.kotlin.Day2Test
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import kotlin.test.assertEquals

@TestInstance(Lifecycle.PER_CLASS)
@Tag(Day2Test)
class ShopTest
