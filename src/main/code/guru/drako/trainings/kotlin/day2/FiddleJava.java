package guru.drako.trainings.kotlin.day2;

public class FiddleJava {
  public static <T> T create(final Class<T> clazz) throws Exception {
    return clazz.getConstructor().newInstance();
  }


  public static class CompanionClass {}

  public static final CompanionClass Companion = new CompanionClass();

  public static void main(String[] args) throws Exception {
    final String s = create(String.class);

    Fiddle.Pisser.add(23, 42);

    // thanks to JvmStatic
    Fiddle.add(23, 42);

    // thanks to JvmField
    final int answer = Fiddle.answer;

    // const val always generates static fields
    final int leet = Fiddle.LEET;

    new Thread(() -> {

    }).join();
  }

  int getAnswer() {
    return 42;
  }

}
