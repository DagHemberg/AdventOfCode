package aoc.y2022.day06
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(6, 2022)(2)(19):
  def name = "Tuning Trouble - Part 2"
  def solve(data: List[String]) = 
    given String = data.head
    findMarker(14)
