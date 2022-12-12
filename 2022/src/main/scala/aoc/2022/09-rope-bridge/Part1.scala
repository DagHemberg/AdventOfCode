package aoc.y2022.day09
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(9, 2022)(1)(13):
  def name = "Rope Bridge - Part 1"
  def solve(data: List[String]) =     
    given Rope = Rope.ofLength(2)
    val visited = parse(data)    
    visited.size
