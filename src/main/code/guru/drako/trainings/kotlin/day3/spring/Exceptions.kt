package guru.drako.trainings.kotlin.day3.spring

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundException(
  val entityType: String,
  val id: Int,
) : ResponseStatusException(HttpStatus.NOT_FOUND, "$entityType#$id does not exist.") {
  companion object {
    inline fun <reified T : Any> of(id: Int) = NotFoundException(
      entityType = T::class.java.simpleName,
      id = id,
    )
  }
}
