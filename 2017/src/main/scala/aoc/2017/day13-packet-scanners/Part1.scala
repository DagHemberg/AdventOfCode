package aoc.y2017.day13
import aoc.utils.*

object Part1 extends Problem("2017", "13", "1")(24):
  def name = "Packet Scanners - Part 1"
  def solve(data: Seq[String]) = 
    val scanners = parse(data)

    scanners.filter(catches).map(_ * _).sum
