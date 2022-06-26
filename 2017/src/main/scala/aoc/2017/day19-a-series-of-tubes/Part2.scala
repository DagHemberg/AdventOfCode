package aoc.y2017.day19
import aoc.utils.*

object Part2 extends Problem("2017", "19", "2")(38):
  def name = "A Series of Tubes - Part 2"
  def solve(data: Seq[String]) = 
    given grid: Matrix[(Char, Pos2D)] = data
      .map(_.toVector)
      .toVector
      .toMatrix
      .zipWithIndex
      
    path.size
    