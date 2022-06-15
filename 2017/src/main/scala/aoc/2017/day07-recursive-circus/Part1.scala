package aoc.y2017.day07
import aoc.utils.*

object Part1 extends Problem("2017", "07", "1")("tknk"):
  def name = "Recursive Circus - Part 1"
  def solve(data: Seq[String]) = 
    getRoot(data)
