package aoc.y2022.day14
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(14, 2022)(1)(24):
  def name = "Regolith Reservoir - Part 1"
  def solve(data: List[String]) = 
    given rocks: Set[Pos2D] = parse(data)
    given bottomLevel: Int = rocks.maxBy(_.y).down.y

    simulateUntil(lastYPos = bottomLevel) - 1
