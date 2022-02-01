package guru.drako.trainings.kotlin.day3;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum PersonProperties {
  FirstName("firstName"),
  LastName("lastName");

  private final String fieldName;

  PersonProperties(final @NotNull String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName);
  }

  public String getName() {
    return fieldName;
  }
}
