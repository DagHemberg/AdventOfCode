package aoc.y2017.day09
import aoc.utils.*

object Part2 extends Problem("2017", "09", "2")(71):
  def name = "Stream Processing - Part 2"
  def solve(data: Seq[String]) = 
    data.flatMap(_
      .replaceAll("!.", "")
      .findAllMatchWith("<(.*?)>")
      .map(_.group(1).size)
    ).sum
