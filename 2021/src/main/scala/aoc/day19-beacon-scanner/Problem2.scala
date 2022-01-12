package aoc.day19
import aoc.utils.*

object Problem2 extends Solver("19", 3621L):
  def name = "Beacon Scanner - Part 2"
  def solve(data: Vector[String]) = 
    val scanners = align(data)
    scanners.map(_.pos).combinations(2).map { case Seq(a, b) => a manhattan b }.max
