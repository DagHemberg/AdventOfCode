package aoc.y2017.day13
import aoc.utils.*

object Part2 extends Problem("2017", "13", "2")(10):
  def name = "Packet Scanners - Part 2"
  def solve(data: Seq[String]) = 
    val scanners = parse(data)

    1.doUntil(delay => 
      def catches(depth: Int, sc: Scanner) = (depth + delay) % (2 * sc.range - 2) == 0
      !scanners.map(catches).exists(identity)
    )(_ + 1)
