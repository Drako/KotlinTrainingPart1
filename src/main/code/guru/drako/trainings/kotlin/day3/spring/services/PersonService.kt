package guru.drako.trainings.kotlin.day3.spring.services

import guru.drako.trainings.kotlin.day3.spring.NotFoundException
import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.entities.PersonEntity
import guru.drako.trainings.kotlin.day3.spring.entities.UpdatePerson
import guru.drako.trainings.kotlin.day3.spring.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PersonService @Autowired constructor(
  private val repository: PersonRepository
) {
  fun clear() {
    repository.deleteAll()
  }

  fun newPerson(newPerson: NewPerson): Person {
    return PersonEntity(firstName = newPerson.firstName, lastName = newPerson.lastName)
      .let { repository.save(it) }
      .toPerson()
  }

  @Transactional
  fun deletePerson(id: Int): Boolean {
    return if (repository.existsById(id)) {
      repository.deleteById(id)
      true
    } else false
  }

  @Transactional
  fun updatePerson(id: Int, update: UpdatePerson): Person {
    if (update.firstName != null) {
      if (update.lastName != null) {
        repository.updateNames(id, update.firstName, update.lastName)
      } else {
        repository.updateFirstName(id, update.firstName)
      }
    } else if (update.lastName != null) {
      repository.updateLastName(id, update.lastName)
    }

    return getPerson(id)
      ?: throw NotFoundException.of<Person>(id)
  }

  fun getPerson(id: Int): Person? {
    return repository.findById(id).map(PersonEntity::toPerson).orElse(null)
  }

  fun getPeople(offset: Int, limit: Int): List<Person> {
    return repository.findAll(PageRequest.of(offset / limit, limit)).map(PersonEntity::toPerson).toList()
  }
}
