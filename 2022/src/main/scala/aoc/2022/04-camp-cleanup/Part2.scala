package aoc.y2022.day04
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(4, 2022)(2)(4):
  def name = "Camp Cleanup - Part 2"
  def solve(data: List[String]) = 
    data.count { case s"$a-$b,$c-$d" => (a to b).exists((c to d).contains) }
