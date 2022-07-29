package guru.drako.trainings.kotlin.day1;


public class Point {
  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(final int x) {
    this.x = x;
  }

  public void setY(final int y) {
    this.y = y;
  }
}
