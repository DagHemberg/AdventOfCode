package aoc.y2017.day07
import aoc.utils.*

object Part1 extends Problem("2017", "07", "1")("tknk"):
  def name = "Recursive Circus - Part 1"
  def solve(data: Seq[String]) = 
    val all = data.map {
      case s"$name $_" => name
    }

    val referenced = data.flatMap {
      case s"$name ($num) -> $ls" => ls.split(", ")
      case s"$name $_" => Array.empty[String]
    }

    (all diff referenced).head
