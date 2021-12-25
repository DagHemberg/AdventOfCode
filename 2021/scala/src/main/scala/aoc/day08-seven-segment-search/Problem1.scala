package aoc.day08
import aoc.utils.*

object Problem1 extends Solver("08", 26):
  def name = "Seven Segment Search - Part 1"
  def solve(data: Vector[String]) = data
    .flatMap { case s"$signalPatterns | $output" => output.split(" ") }
    .map(_.size)
    .groupBy(identity)
    .mapValues(_.size)
    .filter((c, _) => Set(2,3,4,7)(c))
    .map(_._2)
    .sum