package aoc.day15
import aoc.utils.*

object Problem1 extends Solver("15", 40):
  def name = "Chiton - Part 1"

  def solve(data: Vector[String]) =         
    val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix
    given Matrix[Int] = caves

    path.map(caves.apply).sum