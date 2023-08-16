package aoc.y2022.day25
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(25, 2022)(1)("2=-1=0"):
  def name = "Full of Hot Air - Part 1"
  def solve(data: List[String]) = 
    data
      .map(Snafu.apply)
      .reduce(_ + _)
      .str
