package aoc.y2017.day11
import aoc.utils.*

object Part1 extends Problem("2017", "11", "1")(3):
  def name = "Hex Ed - Part 1"
  def solve(data: Seq[String]) = 
    val path = data.head.split(",").toList

    def traverse(ls: List[String]) = 
      ls.foldLeft(start)((pos, dir) => pos move parse(dir))

    (traverse(path) manhattan start) / 2