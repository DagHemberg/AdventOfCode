package aoc.day05
import aoc.utils.*

object Problem1 extends Solver("05", 5):
  def name = "Hydrothermal Venture - Part 1"

  def positions(l: Line) =
    if l.start.x == l.end.x then l.ys.map(y => Pos(l.start.x, y)).toVector
    else if l.start.y == l.end.y then l.xs.map(x => Pos(x, l.start.y)).toVector
    else Vector.empty[Pos]

  def solve(data: Vector[String]) =
    given Vector[String] = data
    given (Line => Vector[Pos]) = positions

    intersections.size
