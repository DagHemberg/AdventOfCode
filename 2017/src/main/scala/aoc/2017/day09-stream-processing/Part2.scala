package aoc.y2017.day09
import aoc.utils.*

object Part2 extends Problem("2017", "09", "2")(71):
  def name = "Stream Processing - Part 2"
  def solve(data: Seq[String]) = 
    data.flatMap(str => "<.*?>".r
      .findAllMatchIn(str.replaceAll("!.", ""))
      .toList
      .map(m => m.toString.substring(1, m.toString.size - 1).size)
    ).sum
