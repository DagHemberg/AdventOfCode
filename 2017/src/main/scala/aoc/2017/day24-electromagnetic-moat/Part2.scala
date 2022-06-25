package aoc.y2017.day24
import aoc.utils.*

object Part2 extends Problem("2017", "24", "2")(19):
  def name = "Electromagnetic Moat - Part 2"
  def solve(data: Seq[String]) = 
    val components = parse(data).map {
      case Component(0, b, s, o, _) => Component(0, b, s, o)
      case Component(a, 0, s, o, _) => Component(0, a, s, o)
      case c => c
    }
    
    val all = buildBridges(components)
    val max = all.maxBy(_.length).length
    all.filter(_.length == max).maxBy(_.strength).strength