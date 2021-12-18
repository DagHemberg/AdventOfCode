package aoc.day18
import aoc.utils.*

object Problem1 extends Solver("18", 4140L):

  case class Pair(first: Pair | Int, second: Pair | Int)

  def name = "Snailfish - Part 1"
  def solve(data: Vector[String]) = 

    1