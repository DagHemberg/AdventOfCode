package aoc.day10
import aoc.utils.*
import Common.*

object Problem2 extends Solver("10", 288957L):
  def name = "Syntax Scoring - Part 2"
  def solve(data: Vector[String]) = data
    .filter(_.isIncomplete)
    .map(str => str
      .filtered
      .reverse
      .map(c => parens(c.toString))
      .foldLeft(0L)((sum, paren) => 5 * sum + incompleteValue(paren)))
    .middle
    