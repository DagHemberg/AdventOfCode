package aoc.y2017.day04
import aoc.utils.*

object Part2 extends Problem("2017", "04", "2")(5):
  def name = "High-Entropy Passphrases - Part 2"
  def solve(data: Seq[String]) = 
    data
      .map(_.words)
      // not the fastest approach, but its pretty concise which im happy with
      .count(ws => !ws.exists(w => w.permutations.exists(ws.diff(Seq(w)).contains)))
