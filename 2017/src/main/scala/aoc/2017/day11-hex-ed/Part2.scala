package aoc.y2017.day11
import aoc.utils.*

object Part2 extends Problem("2017", "11", "2")(3):
  def name = "Hex Ed - Part 2"
  def solve(data: Seq[String]) = 
    val path = data.head.split(",").toList

    def furthestOut(ls: List[String]) = 
      ls.foldLeft((0, start)){ case ((furthest, pos), dir) => 
        val newPos = pos + Pos3D(move(dir))
        (furthest max (newPos manhattan start) / 2, newPos)
      }.head

    furthestOut(path)