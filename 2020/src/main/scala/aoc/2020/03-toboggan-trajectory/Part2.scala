package aoc.y2020.day03
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(3, 2020)(2)(336L):
  def name = "Toboggan Trajectory - Part 2"
  def solve(data: List[String]) = 
    val angles = List((1, 1), (1, 3), (1, 5), (1, 7), (2, 1))
    val matrix = parse(data)
    angles
      .map: angle =>
        calculateTrajectory(matrix, angle).log
          .count(matrix.apply andThen (_ == "#"))
          .toLong
      .product