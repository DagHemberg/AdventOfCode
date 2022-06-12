package aoc.day15
import aoc.utils.*

object Problem1 extends Solver("15", 40):
  def name = "Chiton - Part 1"

  def solve(data: Vector[String]) =         
    val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix

    Pathfinder(graphFrom(caves), Index(0, 0), Index(caves.width - 1, caves.height - 1))
      .shortestPath
      .map(_.cost.toInt)
      .getOrElse(0)