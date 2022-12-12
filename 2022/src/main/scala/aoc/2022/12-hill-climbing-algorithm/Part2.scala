package aoc.y2022.day12
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(12, 2022)(2)(29):
  def name = "Hill Climbing Algorithm - Part 2"
  def solve(data: List[String]) = 
    val matrix = parse(data)
    val graph = makeGraph(matrix)
    
    val stop  = matrix.toVector.collect { case ('E', i) => i }.head
    val starts = matrix.toVector.collect { 
      case ('S', i) => i
      case ('a', i) => i
    }

    starts.map { start => graph
      .pathBetween(start, stop)
      .map(_.cost)
      .getOrElse(Double.PositiveInfinity)
    }.min.toInt
