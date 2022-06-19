package aoc.y2017.day14
import aoc.utils.*

object Part1 extends Problem("2017", "14", "1")(8108):
  def name = "Disk Defragmentation - Part 1"
  def solve(data: Seq[String]) = 
    defrag(data.head).count(_ == '1')
