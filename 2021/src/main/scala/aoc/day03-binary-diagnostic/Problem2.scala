package aoc.day03
import aoc.utils.*

object Problem2 extends Solver("03", 230L):
  def name = "Binary Diagnostic - Part 2"
  def solve(data: Vector[String]) =
    val Seq(oxygen, co2) = Seq(1, 2).map(n => 
      (0 until data.head.size)
        .foldLeft(data)((numbers, i) => 
          val filtered = 
            numbers.filter(_(i) == (if n == 1 
              then mostCommonAt(i)(using numbers) 
              else leastCommonAt(i)(using numbers)))
          if filtered.isEmpty then numbers else filtered)
      .head
      .toDecimal)
      
    oxygen * co2