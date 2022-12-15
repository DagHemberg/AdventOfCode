package aoc.y2022.day13
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(13, 2022)(2)(140):
  def name = "Distress Signal - Part 2"
  def solve(data: List[String]) = 
    val divider1 = Packet.from("[[2]]")
    val divider2 = Packet.from("[[6]]")

    val packets = parse(data)
      .flatten
      .appendedAll(List(divider1, divider2))
      .sorted
    
    val Seq(fst, snd) = Seq(divider1, divider2).map(x => packets.indexOf(x) + 1)    
    fst * snd
