package aoc.day10
import aoc.utils.*
import Common.*

object Problem1 extends Solver("10", 26397L):
  def name = "Syntax Scoring - Part 1"
  def solve(data: Vector[String]) = data
    .filter(_.isCorrupt)
    .map(str => str
      .filtered
      .find(paren => parens.values.toSet(paren.toString)).get // find first closing paren
      .toString)
    .map(corruptValue)
    .sum
