package aoc.day09
import aoc.utils.*

object Problem1 extends Solver("09", 15):
  def name = "Smoke Basin - Part 1"
  def solve(data: Vector[String]) =
    val heights = data.map(_.split("").map(_.toInt).toVector).toMatrix

    val dangerLevels = heights
      .indices
      .toVector
      .flatten
      .filter(index => (heights surrounding index) forall (heights(index) < heights(_)))
      .map(pos => heights(pos) + 1)

    dangerLevels.sum
      