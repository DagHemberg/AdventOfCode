package aoc.y2022.day18
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(18, 2022)(1)(64):
  def name = "Boiling Boulders - Part 1"
  def solve(data: List[String]) = 
    val cubes = parse(data)
    cubes.sumBy(6 - _.adjacent.count(cubes.contains))
