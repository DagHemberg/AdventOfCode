package aoc.y2017.day12
import aoc.utils.*

object Part1 extends Problem("2017", "12", "1")(6):
  def name = "Digital Plumber - Part 1"
  def solve(data: Seq[String]) = 
    val adjacents = parse(data)
    
    Set("0").converge(_ flatMap adjacents).size
