package guru.drako.trainings.kotlin.day1;

import kotlin.text.StringsKt;

public class FiddleJava {
  public static void main(String[] args) {
    FizzBuzzKt.square(23);

    final Foo foo = new Foo("Hello");
    foo.setMessage("Enter something:");
    final String entered = foo.getMessage();
    foo.setMessage(entered);
    foo.isEmpty();
  }
}
