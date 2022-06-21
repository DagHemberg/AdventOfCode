package aoc.y2017.day19
import aoc.utils.*

object Part1 extends Problem("2017", "19", "1")("ABCDEF"):
  def name = "A Series of Tubes - Part 1"
  def solve(data: Seq[String]) =
    given grid: Matrix[(Char, (Int, Int))] = data
      .map(_.toVector)
      .toVector
      .toMatrix
      .zipWithIndex

    path.filter(_.isLetter)