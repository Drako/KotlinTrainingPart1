package guru.drako.trainings.kotlin.day3.spring.repositories

import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.entities.PersonEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : PagingAndSortingRepository<PersonEntity, Int> {
  @Modifying
  @Query("UPDATE PersonEntity p SET p.firstName = ?2 WHERE p.id = ?1")
  fun updateFirstName(id: Int, firstName: String)

  @Modifying
  @Query("UPDATE PersonEntity p SET p.lastName = ?2 WHERE p.id = ?1")
  fun updateLastName(id: Int, lastName: String)

  @Modifying
  @Query("UPDATE PersonEntity p SET p.firstName = ?2, p.lastName = ?3 WHERE p.id = ?1")
  fun updateNames(id: Int, firstName: String, lastName: String)
}
