package aoc.y2022.day12
import paut.aoc.*
import problemutils.*, extensions.*, classes.graphs.*

object Part2 extends Problem(12, 2022)(2)(29):
  def name = "Hill Climbing Algorithm - Part 2"
  def solve(data: List[String]) = 
    val matrix = parse(data)
    val graph = makeGraph(matrix).transpose
    val start = matrix.indexWhere(_ == 'E').get
  
    graph
      .pathFromUntil(start, matrix(_) == 'a')(using graph.edgesFrom)
      .map(_.cost.toInt)
      .getOrElse(-1)
