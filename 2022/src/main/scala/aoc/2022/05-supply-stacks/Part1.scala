package aoc.y2022.day05
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(5, 2022)(1)("CMZ"):
  def name = "Supply Stacks - Part 1"
  def solve(data: List[String]) = 
    given Stacks = parseStacks(data)
    given List[Move] = parseInstructions(data)

    moveCrates(strat = _.reverse)
