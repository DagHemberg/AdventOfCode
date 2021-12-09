package aoc.day01

import aoc.utils.*

object Problem1 extends Solver("01", 7):
  def solve(data: Vector[String]): Int =
    data
      .map(_.toInt)
      .sliding(2)
      .filter(x => x(0) < x(1))
      .size
