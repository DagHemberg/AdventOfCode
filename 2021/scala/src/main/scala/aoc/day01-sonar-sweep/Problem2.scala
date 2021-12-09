package aoc.day01
import aoc.utils.*

object Problem2 extends Solver("01", 5):
  def name = "Sonar Sweep - Part 2"
  def solve(data: Vector[String]) =
    data
      .map(_.toInt)
      .sliding(3)
      .sliding(2)
      // .toVector
      .filter(x => x.head.sum < x.last.sum)
      .size
