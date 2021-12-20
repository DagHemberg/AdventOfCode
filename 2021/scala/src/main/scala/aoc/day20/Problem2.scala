package aoc.day20
import aoc.utils.*

object Problem2 extends Solver("20", 3351L):
  def name = "Trench Map - Part 2"
  def solve(data: Vector[String]) = 
    given algorithm: String = data.head
    given startingImage: Matrix[String] = data
      .drop(2)
      .map(_.split("").toVector)
      .toMatrix

    (1 to 50).foldLeft(startingImage)((image, round) => image.enhance(round)).count(_ == "#")