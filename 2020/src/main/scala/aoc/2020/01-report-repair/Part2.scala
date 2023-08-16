package aoc.y2020.day01
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(1, 2020)(2)(241861950L):
  def name = "Report Repair - Part 2"
  def solve(data: List[String]) = calculate(data, 3)
