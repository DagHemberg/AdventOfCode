package aoc.day07
import aoc.utils.*

object Problem2 extends Solver("07", 168):
  def name = "Crab Alignment - Part 2"
  def solve(data: Vector[String]) = 
    val crabs = data.head.split(",").toVector.map(_.toInt)
    (1 to crabs.max).map(diff => crabs.map(pos => (1 to (pos - diff).abs).sum).sum).min