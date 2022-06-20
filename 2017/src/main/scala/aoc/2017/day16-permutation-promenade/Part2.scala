package aoc.y2017.day16
import aoc.utils.*

object Part2 extends Problem("2017", "16", "2")("ghidjklmnopabcef"):
  def name = "Permutation Promenade - Part 2"
  def solve(data: Seq[String]) = 
    val instructions = parse(data)
    given Seq[String] = instructions

    val cycles = (1, programs.dance).doUntil
      ((_, str) => str == programs)
      ((seen, str) => (seen + 1, str.dance))
      .head
    
    programs.iterate(_.dance)(1_000_000_000 % cycles)
