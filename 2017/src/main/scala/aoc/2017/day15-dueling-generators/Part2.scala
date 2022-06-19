package aoc.y2017.day15
import aoc.utils.*

object Part2 extends Problem("2017", "15", "2")(309):
  def name = "Dueling Generators - Part 2"
  def solve(data: Seq[String]) =
    val Seq(a, b) = parse(data)
    generate(a, b, 5_000_000, 4, 8)
