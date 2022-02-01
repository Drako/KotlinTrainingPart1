package guru.drako.trainings.kotlin.day3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Point2D<T> {
  private final T x;
  private final T y;

  public Point2D(@NotNull T x, @NotNull T y) {
    this.x = Objects.requireNonNull(x);
    this.y = Objects.requireNonNull(y);
  }

  public Point2D(@Nullable T x) {
    this(x, null);
  }

  public Point2D() {
    this(null);

    final T[] arr = null;
    setAll(arr);
  }

  public void setAll(T... values) {
    final T x = values[0];
  }
}
