package aoc.y2017.day24
import aoc.utils.*

object Part1 extends Problem("2017", "24", "1")(31):
  def name = "Electromagnetic Moat - Part 1"
  def solve(data: Seq[String]) = 
    val components = parse(data).map {
      case Component(0, b, s, o, _) => Component(0, b, s, o)
      case Component(a, 0, s, o, _) => Component(0, a, s, o)
      case c => c
    }

    buildBridges(components).map(_.strength).max
