package aoc.y2017.day17
import aoc.utils.*

object Part2 extends Problem("2017", "17", "2")(1222153):
  def name = "Spinlock - Part 2"
  def solve(data: Seq[String]) = 
    given steps: Int = data.head.toInt
    var num = 1
    var pos = 0
    
    for n <- 1 to 50_000_000 do
      if jump(n, pos) == 0 then num = n
      pos = jump(n, pos) + 1

    num
