package aoc.y2022.day03
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(3, 2022)(1)(157):
  def name = "Rucksack Reorganization - Part 1"
  def solve(data: List[String]) = data
    .sumBy: rucksack => 
      val (first, second) = rucksack.splitAt(rucksack.size / 2)
      priority(first.intersect(second).head)
