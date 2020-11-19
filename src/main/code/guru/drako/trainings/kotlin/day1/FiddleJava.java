package guru.drako.trainings.kotlin.day1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static guru.drako.trainings.kotlin.day1.FiddleKt.add;

public class FiddleJava {
  public static void main(String[] args) {
    Foo.getFoo();

    add(23, 42);

    final Point p = new Point(10, 20);
  }

  @NotNull List<Integer> getNumbers() {
    return List.of(1, 2, 3);
  }
}
