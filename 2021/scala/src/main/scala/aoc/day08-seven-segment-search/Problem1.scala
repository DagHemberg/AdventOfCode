package aoc.day08

import aoc.utils.Solver

object Problem1 extends Solver("08", 26):
  def solve(data: Vector[String]) = data
    .flatMap { case s"$signalPatterns | $output" => output.split(" ") }
    .map(_.size)
    .groupBy(identity)
    .mapValues(_.size)
    .toVector
    .filter(Vector(2,3,4,7) contains _._1)
    .map(_._2)
    .sum