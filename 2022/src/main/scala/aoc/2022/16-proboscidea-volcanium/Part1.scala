package aoc.y2022.day16
import paut.aoc.*
import problemutils.*, extensions.*, classes.graphs.*

object Part1 extends Problem(16, 2022)(1)(1651):
  def name = "Proboscidea Volcanium - Part 1"
  def solve(data: List[String]) = 
    val tunnels = getTunnels(data)
    val flow    = getFlow(data)
    val max     = flow.values.sum

    given Map[String, Set[String]] = tunnels
    given Map[String, Int]         = flow

    val start = State("AA", Set.empty, 1)
    def next(state: State) = state.fromHere.map(next => Edge(state, next, state.cost))

    Graph
      .generateWith(next)
      .pathFromUntil(start, _.elapsedTime == 30)(using next).log
      .map(_.vertices.sumBy(_.openValves.sumBy(flow)).toInt)
      .getOrElse(0)
