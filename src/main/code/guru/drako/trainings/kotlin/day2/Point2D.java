package guru.drako.trainings.kotlin.day2;

import java.util.Objects;

// data class Point2D(val x: Int, val y: Int)
public final class Point2D {
  @Override
  public String toString() {
    return "Point2D{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

  public Point2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  private final int x;
  private final int y;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point2D point2D = (Point2D) o;
    return x == point2D.x && y == point2D.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
