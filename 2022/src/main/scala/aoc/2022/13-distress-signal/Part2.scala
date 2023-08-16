package aoc.y2022.day13
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(13, 2022)(2)(140):
  def name = "Distress Signal - Part 2"
  def solve(data: List[String]) = 
    val dividers = Seq("[[2]]", "[[6]]").map(Packet.parse)

    val packets = parse(data)
      .flatten
      .appendedAll(dividers)
      .sorted
    
    dividers
      .map(packet => packets.indexOf(packet) + 1)
      .product 