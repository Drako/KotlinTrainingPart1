package guru.drako.trainings.kotlin.day2

operator fun Point2D.plus(other: Point2D) = Point2D(
  x + other.x,
  y + other.y
)
