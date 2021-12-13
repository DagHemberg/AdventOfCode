package aoc.day13
import aoc.utils.*

object Problem1 extends Solver("13", 17):
  def name = "Transparent Origami - Part 1"
  def solve(data: Vector[String]) =
    val points = data
      .takeWhile(_.nonEmpty)
      .map { case s"$x,$y" => Pos(x.toInt, y.toInt) }

    val commands = data
      .reverse
      .takeWhile(_.nonEmpty)
      .reverse
      .map { case s"fold along $dir=$value" => Command(dir, value.toInt) }

    val pointsFoldedOnce = commands.head match
      case Command("x", n) => points.map(p => if p.x < n then p else Pos(2 * n - p.x, p.y))
      case Command("y", n) => points.map(p => if p.y < n then p else Pos(p.x, 2 * n - p.y))

    pointsFoldedOnce.distinct.size