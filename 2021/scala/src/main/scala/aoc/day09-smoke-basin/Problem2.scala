package aoc.day09
import aoc.utils.*

object Problem2 extends Solver("09", 1134):
  def name = "Smoke Basin - Part 2"
  def solve(data: Vector[String]) =
    val heights = data.map(_.split("").map(_.toInt).toVector).toMatrix

    val lowPoints = heights
      .indices
      .toVector
      .flatten
      .filter(index => (heights surrounding index) forall (heights(index) < heights(_)))

    val basins = lowPoints.map(pos =>
      val collected = scala.collection.mutable.Set(pos)
      var prevSize = 0
      while collected.size != prevSize do
        prevSize = collected.size
        collected.foreach(ind =>
          heights
            .surrounding(ind)
            .toSet
            .diff(collected)
            .foreach(index => if heights(index) != 9 then collected += index)
        )

      collected.size
    )

    basins.sorted.reverse.take(3).product
