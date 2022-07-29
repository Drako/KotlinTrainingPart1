package guru.drako.trainings.kotlin.day2;

public class FiddleJava {
  public static void main(String[] args) {
    Foo.Companion.foo();
    Foo.foo();

    System.out.println(Foo.Companion.getPi());
    System.out.println(Foo.getPi());

    System.out.println(Foo.answer);
    //System.out.println(Foo.Companion.getAnswer());

    System.out.println(Foo.MESSAGE);

    Bar.INSTANCE.bar();
  }
}
