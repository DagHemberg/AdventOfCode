package aoc.y2017.day03
import aoc.utils.*

object Part2 extends Problem("2017", "03", "2")(1968):
  def name = "Spiral Memory - Part 2"
  def solve(data: Seq[String]) = 
    val num = data.head.toInt
    val init = Map((0, 0) -> 1)

    val (n: Int, values: Map[(Int, Int), Int]) = (1, init).doUntil
      ((i, m) => m(spiralPos(i)) > num)
      ((i, m) => (i + 1, m + (spiralPos(i+1) -> spiralPos(i+1).neighbours.flatMap(m.get).sum)))
    
    values apply spiralPos(n)