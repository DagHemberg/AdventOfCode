package aoc.day05
import aoc.utils.*
import Common.*

object Problem2 extends Solver("05", 12):
  def name = "Hydrothermal Venture - Part 2"

  extension (l: Line) def positions =
      if l.start.x == l.end.x then l.ys.map(y => Pos(l.start.x, y)).toVector
      else if l.start.y == l.end.y then l.xs.map(x => Pos(x, l.start.y)).toVector
      else if l.xs.size == l.ys.size then l.xs zip l.ys map Pos.apply
      else Vector.empty[Pos]

  def solve(data: Vector[String]) =

    val lines = data.map {
        case s"$x1,$y1 -> $x2,$y2" => Line(Pos(x1.toInt, y1.toInt), Pos(x2.toInt, y2.toInt))
      }

    val allPositions = lines.flatMap(_.positions)
    val intersections = allPositions
      .groupBy(identity)
      .filter((id, coll) => coll.size > 1)
      .keys
      .toVector

    intersections.size
