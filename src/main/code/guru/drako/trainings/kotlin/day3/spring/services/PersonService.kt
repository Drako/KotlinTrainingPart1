package guru.drako.trainings.kotlin.day3.spring.services

import guru.drako.trainings.kotlin.day3.spring.NotFoundException
import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
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
  private var nextId = 1

  fun clear() {
    repository.deleteAll()
    nextId = 1
  }

  fun newPerson(newPerson: NewPerson): Person {
    return Person(id = nextId++, firstName = newPerson.firstName, lastName = newPerson.lastName)
      .also { repository.save(it) }
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
    return repository.findById(id).orElse(null)
  }

  fun getPeople(offset: Int, limit: Int): List<Person> {
    return repository.findAll(PageRequest.of(offset / limit, limit)).toList()
  }
}
