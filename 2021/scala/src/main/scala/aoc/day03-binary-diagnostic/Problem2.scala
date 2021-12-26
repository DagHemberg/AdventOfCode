package aoc.day03
import aoc.utils.*

object Problem2 extends Solver("03", 230L):
  def name = "Binary Diagnostic - Part 2"
  def solve(data: Vector[String]) =
    given Vector[String] = data
    
    val Seq(oxygen, co2) = Seq(1, 2).map(n => (0 until data.head.size)
      .foldLeft(data)((acc, i) => 
        val filtered = 
          acc.filter(_(i) == (if n == 1 
            then mostCommonAt(i)(using acc) 
            else leastCommonAt(i)(using acc))
          )
        if filtered.isEmpty then acc else filtered
      )
      .head
      .toDecimal)
      
    oxygen * co2