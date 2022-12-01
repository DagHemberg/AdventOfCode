package aoc.y2022.day01
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(1, 2022)(2)(45000):
  def name = "Calorie Counting - Part 2"
  def solve(data: List[String]) = parse(data).sorted.reverse.take(3).sum
