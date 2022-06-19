package aoc.y2017.day15
import aoc.utils.*

object Part1 extends Problem("2017", "15", "1")(588):
  def name = "Dueling Generators - Part 1"
  def solve(data: Seq[String]) =
    val Seq(a, b) = parse(data)
    generate(a, b, 40_000_000)
