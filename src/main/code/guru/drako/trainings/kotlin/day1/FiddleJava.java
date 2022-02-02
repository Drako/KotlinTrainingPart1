package guru.drako.trainings.kotlin.day1;

import kotlin.text.StringsKt;

public class FiddleJava extends java.lang.Object {
  public static void main(String[] args) {
    
  }

  int foo(int a) {

    Foo.Companion.foo();
    //Foo.Companion.getANSWER();
    final int answer = Foo.ANSWER;

    StringsKt.isBlank("");

    return Fiddle.subtract(a, 23);
  }

  // fun abs(n: Int) = if (n < 0) -n else n
  int abs(final int n) {
    return n < 0 ? -n : n;
  }
}
