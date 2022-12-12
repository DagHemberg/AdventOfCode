package aoc.y2022.day12
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(12, 2022)(1)(31):
  def name = "Hill Climbing Algorithm - Part 1"
  def solve(data: List[String]) = 
    val matrix = parse(data)
    val graph = makeGraph(matrix)

    val stop  = matrix.toVector.collect { case ('E', i) => i }.head
    val start = matrix.toVector.collect { case ('S', i) => i }.head
    
    graph
      .pathBetween(start, stop)
      .map(_.cost.toInt)
      .getOrElse(-1)
