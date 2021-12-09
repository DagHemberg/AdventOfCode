package aoc.day05
import aoc.utils.*

object Problem1 extends Solver("05", 5):
  def name = "Hydrothermal Venture - Part 1"

  case class Pos(x: Int, y: Int)

  case class Line(start: Pos, end: Pos):

    private def range(st: Int, en: Int): Vector[Int] =
      (st to en by (if st < en then 1 else -1)).toVector

    private val (xs, ys) = (range(start.x, end.x), range(start.y, end.y))

    def positions =
      if start.x == end.x then ys.map(y => Pos(start.x, y)).toVector
      else if start.y == end.y then xs.map(x => Pos(x, start.y)).toVector
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
