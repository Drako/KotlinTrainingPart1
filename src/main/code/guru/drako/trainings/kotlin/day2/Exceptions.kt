package guru.drako.trainings.kotlin.day2

@Suppress("NonAsciiCharacters")
inline fun <T: Throwable> `(╯°□°）╯︵`(block: () -> T): Nothing {
  throw block()
}

class Expired(
  message: String? = null,
  cause: Throwable? = null,
) : RuntimeException(message, cause)
