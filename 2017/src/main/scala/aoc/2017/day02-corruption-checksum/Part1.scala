package aoc.y2017.day02
import aoc.utils.*

object Part1 extends Problem("2017", "02", "1")(18):
  def name = "Corruption Checksum - Part 1"
  def solve(data: Seq[String]) = 
    data
      .map(_.words.map(_.toInt))
      .map(n => n.max - n.min)
      .sum

