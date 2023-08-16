package aoc.y2022.day04
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(4, 2022)(1)(2):
  def name = "Camp Cleanup - Part 1"
  def solve(data: List[String]) = 
    data.count:
      case s"$a-$b,$c-$d" => 
        val (r1, r2) = (a to b, c to d)
        r1.forall(r2.contains) || r2.forall(r1.contains)  
