package guru.drako.trainings.kotlin.day2;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FiddleJava {

  @Nullable
  public static Integer answer() {
    return 42;
  }

  public static void main(String[] args) {
    Math.sqrt(42.0);

    final int answer = Foo.ANSWER;

    final int bar = Foo.bar;

    Foo.say();

    new Runnable() {
      @Override
      public void run() {

      }
    }.run();
  }
}
