package guru.drako.trainings.kotlin.day1;

public class FiddleJava {
  public static void main(String[] args) {
    /*
    final var answer = Fiddle.add(19, 23);

    final var p = new Point(23, 42, 1337);
    final var ergebnis = p == p;
    final var erg2 = p.equals(p);
    // p?.equals(p2) ?: p2 == null
     */
  }

  @Override
  public String toString() {
    try {
      // return create<String>();
      return create(String.class);
    } catch (final Exception ex) {
      return "Main application";
    }
  }

  /*
  <T> T create() throws Exception {
    return T.class.getConstructor().newInstance();
  }
  */

  <T> T create(final Class<T> clazz) throws Exception {
    return clazz.getConstructor().newInstance();
  }
}
