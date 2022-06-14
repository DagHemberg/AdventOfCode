package aoc.y2017.day04
import aoc.utils.*

object Part1 extends Problem("2017", "04", "1")(2):
  def name = "High-Entropy Passphrases - Part 1"
  def solve(data: Seq[String]) = 
    data
      .map(_.words)
      .count(p => p.distinct.size == p.size)
