package aoc.day05
import aoc.utils.*

object Problem1 extends Solver("05", 5):
  def name = "Hydrothermal Venture - Part 1"

  extension (l: Line) def positions =
    if l.start.x == l.end.x then l.ys.map(y => Pos(l.start.x, y)).toVector
    else if l.start.y == l.end.y then l.xs.map(x => Pos(x, l.start.y)).toVector
    else Vector.empty[Pos]

  def solve(data: Vector[String]) =
    val lines = data.map { case s"$x1,$y1 -> $x2,$y2" => Line(Pos(x1.toInt, y1.toInt), Pos(x2.toInt, y2.toInt)) }
    val allPositions = lines.flatMap(_.positions)
    val intersections = allPositions
      .groupBy(identity)
      .filter((id, coll) => coll.size > 1)
      .keys
      .toVector

    intersections.size
