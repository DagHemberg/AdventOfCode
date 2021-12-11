package aoc.day10
import aoc.utils.*
import Common.*

object Problem2 extends Solver("10", 288957L):
  def name = "Syntax Scoring - Part 2"
  def solve(data: Vector[String]) = 
    val results = data
      .filter(_.isIncomplete)
      .map(_
        .filtered
        .reverse
        .map(c => parens(c.toString))
        .foldLeft(0L)((acc, c) => 5 * acc + incompleteValue(c.toString)))
      .sorted
      
    results(results.size / 2)