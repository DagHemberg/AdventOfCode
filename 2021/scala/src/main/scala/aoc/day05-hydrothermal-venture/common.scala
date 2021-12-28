package aoc.day05
import aoc.utils.*

extension (l: Line)
  def range(st: Int, en: Int): Vector[Int] =
    (st to en by (if st < en then 1 else -1)).toVector

  def xs: Vector[Int] = range(l.start.x, l.end.x)
  def ys: Vector[Int] = range(l.start.y, l.end.y)

def intersections(using data: Vector[String], getPositions: Line => Vector[Pos]) = 
  data
    .map { case s"$x1,$y1 -> $x2,$y2" => Line(Pos(x1.toInt, y1.toInt), Pos(x2.toInt, y2.toInt)) }
    .flatMap(getPositions)
    .groupBy(identity)
    .filter((pos, allPos) => allPos.size > 1)
    .keys
    .toVector