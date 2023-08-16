package aoc.y2022.day23
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(23, 2022)(2)(20):
  def name = "Unstable Diffusion - Part 2"
  def solve(data: List[String]) = 
    val grove = parse(data)
    grove.converge(_.move).round + 1
