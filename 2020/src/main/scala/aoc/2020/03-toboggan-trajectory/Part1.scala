package aoc.y2020.day03
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(3, 2020)(1)(7):
  def name = "Toboggan Trajectory - Part 1"
  def solve(data: List[String]) = 
    val angle = (1, 3)
    val matrix = parse(data)
    calculateTrajectory(matrix, angle).count(matrix.apply andThen (_ == "#"))
