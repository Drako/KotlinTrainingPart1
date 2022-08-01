package guru.drako.trainings.kotlin.day3.spring.services

import guru.drako.trainings.kotlin.day3.spring.entities.NewPerson
import guru.drako.trainings.kotlin.day3.spring.entities.Person
import guru.drako.trainings.kotlin.day3.spring.entities.UpdatePerson
import org.springframework.stereotype.Service
import java.time.Period

@Service
class PersonService {
  private val people = mutableListOf<Person>()
  private var nextId = 1

  fun newPerson(newPerson: NewPerson): Person {
    return Person(id = nextId++, firstName = newPerson.firstName, lastName = newPerson.lastName)
      .also { person -> people.add(person) }
  }

  fun deletePerson(id: Int): Boolean {
    return people.removeIf { it.id == id }
  }

  fun updatePerson(id: Int, update: UpdatePerson): Person? {
    return people.firstOrNull { it.id == id }
      ?.apply {
        update.firstName?.also { firstName = it }
        update.lastName?.also { lastName = it }
      }
  }

  fun getPerson(id: Int): Person? {
    return people.firstOrNull { it.id == id }
  }

  fun getPeople(offset: Int, limit: Int): List<Person> {
    return people.asSequence()
      .drop(offset)
      .take(limit)
      .toList()
  }
}
