package guru.drako.trainings.kotlin.day1;

import java.util.ArrayList;
import java.util.List;

public class FiddleJava {
  public FiddleJava plus(final FiddleJava other) {
    return other;
  }

  int increase(int n, int by) {
    return n + by;
  }

  int increase(int n) {
    return increase(n, 1);
  }

  void foo() {
    Fiddle.increase(1);
    Fiddle.increase(1, 2);

    final Point p = new Point(23, 42);
    p.setX(1337);
    final int x = p.getX();

    final List<String> words = new ArrayList<>();
    for (String word : words) {
      continue;
      // break;
      // return;
    }

    words.forEach((word) -> {
      // continue;
      //break;
      return; // continue
    });
  }
}
