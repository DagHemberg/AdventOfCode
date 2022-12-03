package aoc.y2022.day03
import problemutils.*, extensions.*

val priority = 
  val lower = ('a' to 'z').zipWithIndex.map((c, p) => c.toString -> (p + 1)).toMap
  val upper = ('A' to 'Z').zipWithIndex.map((c, p) => c.toString -> (p + 27)).toMap
  lower ++ upper

object Part1 extends Problem(3, 2022)(1)(157):
  def name = "Rucksack Reorganization - Part 2"
  def solve(data: List[String]) = data
    .map { rucksack => 
      val (first, second) = rucksack.splitAt(rucksack.size / 2)
      priority(first.intersect(second).distinct)
    }.sum

object Part2 extends Problem(3, 2022)(2)(70):
  def name = "Rucksack Reorganization - Part 2"
  def solve(data: List[String]) = 
    data
      .sliding(3, 3)
      .map(rucksack => priority(rucksack.reduce(_ intersect _).distinct))
      .sum
