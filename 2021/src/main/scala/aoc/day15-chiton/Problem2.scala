package aoc.day15
import aoc.utils.*

object Problem2 extends Solver("15", 315):
  def name = "Chiton - Part 2"

  def solve(data: Vector[String]) = 
    val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix
    val manyCaves = Matrix(5,5)(_ + _).map(sector => caves.map(x => (x + sector - 1) % 9 + 1)).flatten

    Pathfinder(graphFrom(manyCaves), Index(0, 0), Index(manyCaves.width - 1, manyCaves.height - 1))
      .shortestPath
      .map(_.cost.toInt)
      .getOrElse(0)