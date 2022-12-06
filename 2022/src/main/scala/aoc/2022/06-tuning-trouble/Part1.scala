package aoc.y2022.day06
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(6, 2022)(1)(7):
  def name = "Tuning Trouble - Part 1"
  def solve(data: List[String]) = 
    given String = data.head
    findMarker(4)
