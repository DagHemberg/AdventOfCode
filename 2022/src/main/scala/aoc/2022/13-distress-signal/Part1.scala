package aoc.y2022.day13
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(13, 2022)(1)(13):
  def name = "Distress Signal - Part 1"
  def solve(data: List[String]) = 
    val packets = parse(data)

    packets
      .zipWithIndex
      .sumBy { 
        case (Seq(fst, snd), i) if fst < snd => i + 1 
        case _ => 0
      }
