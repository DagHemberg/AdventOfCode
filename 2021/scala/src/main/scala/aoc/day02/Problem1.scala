package aoc.day02

import aoc.utils.*

object Problem1 extends Solver("02", 150):
  def solve(data: Vector[String]) =
    val horizontals = data filter (_.startsWith("forward"))
    val verticals = data diff horizontals
    val horizontalPos = horizontals
      .map(x => x.split(" ")(1).toInt)
      .sum

    val depth = verticals
      .map(x => x.split(" "))
      .map { case Array(a, b) => if a == "up" then -(b.toInt) else b.toInt }
      .sum

    horizontalPos * depth
