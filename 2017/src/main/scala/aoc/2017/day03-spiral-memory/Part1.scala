package aoc.y2017.day03
import aoc.utils.*

object Part1 extends Problem("2017", "03", "1")(31):
  def name = "Spiral Memory - Part 1"
  def solve(data: Seq[String]) = 
    val num = data.head.toInt
    
    val (row, col) = spiralPos(num)
    row.abs + col.abs
