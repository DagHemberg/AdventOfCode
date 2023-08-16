package aoc.y2022.day03
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(3, 2022)(2)(70):
  def name = "Rucksack Reorganization - Part 2"
  def solve(data: List[String]) = data
    .grouped(3)
    .sumBy(rucksacks => priority(rucksacks.reduce(_ intersect _).head))
