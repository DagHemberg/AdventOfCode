package aoc.day18
import aoc.utils.*

object Problem1 extends Solver("18", 4140L):

  def name = "Snailfish - Part 1"
  def solve(data: Vector[String]) = 
    val numbers = data.map(_.addIndex.parse())
    numbers.reduce(_ + _).magnitude