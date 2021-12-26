package aoc.day03
import aoc.utils.*

object Problem1 extends Solver("03", 198L):
  def name = "Binary Diagnostic - Part 1"
  def solve(data: Vector[String]) =
    given Vector[String] = data

    val gamma = (for i <- data.head.indices yield mostCommonAt(i)).mkString.toDecimal
    val epsilon = gamma.toBinaryString.map(opposite).mkString.toDecimal

    gamma * epsilon