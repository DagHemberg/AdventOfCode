package aoc.y2017.day02
import aoc.utils.*

object Part2 extends Problem("2017", "02", "2")(9):
  def name = "Corruption Checksum - Part 2"
  def solve(data: Seq[String]) = 
    val ints = data.map(_.words.map(_.toInt))
    ints.map(line => 
      val x = line.find(x => line.find(y => x != y && x % y == 0).isDefined).get
      val y = line.filter(x != _).find(y => x % y == 0).get
      x / y
    ).sum
