package aoc.day05
import aoc.utils.*

object Problem2 extends Solver("05", 12):
  def name = "Hydrothermal Venture - Part 2"

  extension (l: Line) def positions =
    if l.start.x == l.end.x then l.ys.map(y => Pos(l.start.x, y)).toVector
    else if l.start.y == l.end.y then l.xs.map(x => Pos(x, l.start.y)).toVector
    else if l.xs.size == l.ys.size then l.xs zip l.ys map Pos.apply
    else Vector.empty[Pos]

  def solve(data: Vector[String]) = 
    given Vector[String] = data
    given (Line => Vector[Pos]) = positions

    intersections.size
