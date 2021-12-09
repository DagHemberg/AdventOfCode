package aoc.day09

import aoc.utils.*

object Problem1 extends Solver("09", 15):
  def solve(data: Vector[String]) =
    val matrix = Matrix(data.map(_.split("").map(_.toInt).toVector))

    matrix.indices
      .map(x => (matrix.surround(x._1, x._2), x))
      .filter((surrounding, index) =>
        surrounding.forall(matrix(index) < matrix(_, _))
      )
      .map(_._2)
      .map(matrix.apply)
      .map(_ + 1)
      .sum
      