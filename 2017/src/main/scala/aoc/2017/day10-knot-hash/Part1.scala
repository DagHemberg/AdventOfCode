package aoc.y2017.day10
import aoc.utils.*

object Part1 extends Problem("2017", "10", "1")(2):
  def name = "Knot Hash - Part 1"
  def solve(data: Seq[String]) =
    val lengths = data.head.split(",").map(_.toInt).toList
    
    hash(lengths)
      .take(2)
      .reduce(_ * _)
