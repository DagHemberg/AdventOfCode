package aoc.day18
import aoc.utils.*

object Problem2 extends Solver("18", 3993L):
  def name = "Snailfish - Part 2"
  def solve(data: Vector[String]) = 
    val numbers = data.map(_.addIndex.parse())
    
    // warning: slow
    numbers.flatMap(num => numbers.filter(_ != num).map(other => (other + num).magnitude)).max