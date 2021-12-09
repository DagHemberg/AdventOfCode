package aoc.day09

import aoc.utils.*

object Problem2 extends Solver("09", 1134):
  def solve(data: Vector[String]) =
    val matrix = Matrix(data.map(_.split("").map(_.toInt).toVector))

    val lowPoints = matrix.indices
      .map(i => (matrix surrounding (i.row, i.col), i))
      .filter((surrounding, index) => surrounding forall (matrix(index) < matrix(_)))
      .map((_, pos) => pos)

    val basins = lowPoints.map(pos =>
      val collected = scala.collection.mutable.Set(pos)
      var prevSize = 0
      while collected.size != prevSize do
        prevSize = collected.size
        collected.foreach(ind =>
          matrix
            .surrounding(ind.row, ind.col)
            .toSet
            .diff(collected)
            .foreach(index => if matrix(index) != 9 then collected += index)
        )

      collected.size
    )

    basins.sorted.reverse.take(3).product
