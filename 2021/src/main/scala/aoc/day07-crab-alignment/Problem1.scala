package aoc.day07
import aoc.utils.*

object Problem1 extends Solver("07", 37):
  def name = "Crab Alignment - Part 1"
  def solve(data: Vector[String]) = 
    val crabs = data.head.split(",").toVector.map(_.toInt)
    (1 to crabs.max).map(diff => crabs.map(pos => (pos - diff).abs).sum).min