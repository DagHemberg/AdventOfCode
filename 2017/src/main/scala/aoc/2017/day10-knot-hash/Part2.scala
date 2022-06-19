package aoc.y2017.day10
import aoc.utils.*

object Part2 extends Problem("2017", "10", "2")("4a19451b02fb05416d73aea0ec8c00c0"):
  def name = "Knot Hash - Part 2"
  def solve(data: Seq[String]) = 
    hash(data.head)
