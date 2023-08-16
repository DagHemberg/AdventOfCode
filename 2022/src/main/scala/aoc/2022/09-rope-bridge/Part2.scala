package aoc.y2022.day09
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(9, 2022)(2)(36, i = 2):
  def name = "Rope Bridge - Part 2"
  def solve(data: List[String]) = 
    given Rope = Rope.ofLength(10)
    val visited = simulate(data)
    visited.size
