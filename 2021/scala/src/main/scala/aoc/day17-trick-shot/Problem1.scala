package aoc.day17
import aoc.utils.*

object Problem1 extends Solver("17", 45):
  def name = "Trick Shot - Part 1"
  def solve(data: Vector[String]) = 
    val minY = raw"(?<=y=)(.*?)(?=\.)".r.findFirstIn(data.head).get.toInt
    (1 to -(minY + 1)).sum
