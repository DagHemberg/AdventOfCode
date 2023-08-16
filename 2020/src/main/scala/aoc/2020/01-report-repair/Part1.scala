package aoc.y2020.day01
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(1, 2020)(1)(514579L):
  def name = "Report Repair - Part 1"
  def solve(data: List[String]) = calculate(data, 2)
