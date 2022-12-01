package aoc.y2022.day01
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(1, 2022)(1)(24000):
  def name = "Calorie Counting - Part 1"
  def solve(data: List[String]) = parse(data).max
