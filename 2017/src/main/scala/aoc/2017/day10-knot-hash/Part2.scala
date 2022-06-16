package aoc.y2017.day10
import aoc.utils.*

object Part2 extends Problem("2017", "10", "2")("4a19451b02fb05416d73aea0ec8c00c0"):
  def name = "Knot Hash - Part 2"
  def solve(data: Seq[String]) = 
    val lengths = (1 to 64).flatMap(x => data.head.map(_.toInt).toList ++ List(17, 31, 73, 47, 23)).toList
    
    hash(lengths)
      .sliding(16, 16)
      .map(_.reduce(_ ^ _).toHexString.padLeftTo(2, '0'))
      .mkString
