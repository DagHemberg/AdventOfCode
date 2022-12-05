package aoc.y2022.day05
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(5, 2022)(2)("MCD"):
  def name = "Supply Stacks - Part 2"
  def solve(data: List[String]) =
    given Stacks = parseStacks(data)
    given List[Move] = parseInstructions(data)

    moveCrates(strat = identity)
