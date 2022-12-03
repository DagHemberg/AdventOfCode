package aoc.y2022.day03
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(3, 2022)(2)(70):
  def name = "Rucksack Reorganization - Part 2"
  def solve(data: List[String]) = 
    data
      .sliding(3, 3)
      .map(rucksack => priority(rucksack.reduce(_ intersect _).head))
      .sum
