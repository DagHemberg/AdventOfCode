package aoc.y2017.day13
import aoc.utils.*

object Part2 extends Problem("2017", "13", "2")(10):
  def name = "Packet Scanners - Part 2"
  def solve(data: Seq[String]) =
    val scanners = parse(data)

    1.doUntil
      (dl => !scanners.exists((dp, rng) => catches(dp, rng)(using dl)))
      (_ + 1)
