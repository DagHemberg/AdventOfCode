package aoc.day20
import aoc.utils.*

object Problem1 extends Solver("20", 35):
  def name = "Trench Map - Part 1"
  def solve(data: Vector[String]) = 
    given algorithm: String = data.head
    given startingImage: Matrix[String] = data
      .drop(2)
      .map(_.split("").toVector)
      .toMatrix

    startingImage.enhance(1).enhance(2).count(_ == "#")