package guru.drako.trainings.kotlin.day2

class NotFoundException(
  message: String? = null,
  cause: Throwable? = null,
) : RuntimeException(message, cause)

class Expired(
  message: String? = null,
  cause: Throwable? = null,
) : RuntimeException(message, cause)
