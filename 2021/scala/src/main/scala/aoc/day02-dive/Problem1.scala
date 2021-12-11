package aoc.day02
import aoc.utils.*

object Problem1 extends Solver("02", 150):
  def name = "Dive! - Part 1"
  def solve(data: Vector[String]) =
    val horizontals = data filter (_.startsWith("forward"))
    val verticals = data diff horizontals
    val horizontalPos = horizontals
      .map(x => x.split(" ").last.toInt)
      .sum

    val depth = verticals
      .map { case s"$command $value" => value.toInt * (if command == "up" then -1 else 1) }
      .sum

    horizontalPos * depth
