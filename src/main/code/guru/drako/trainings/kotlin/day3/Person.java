package guru.drako.trainings.kotlin.day3;

public class Person {
  private final String firstName;
  private final String lastName;

  private Person() {
    this.firstName = null;
    this.lastName = null;
  }

  Person(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
