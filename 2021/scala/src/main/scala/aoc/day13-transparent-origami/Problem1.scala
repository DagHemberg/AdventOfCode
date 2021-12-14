package aoc.day13
import aoc.utils.*

object Problem1 extends Solver("13", 17):
  def name = "Transparent Origami - Part 1"
  def solve(data: Vector[String]) =
    given Vector[String] = data
    val folded = fold(points, commands.take(1))

    folded.size
