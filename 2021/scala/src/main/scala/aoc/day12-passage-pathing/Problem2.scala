package aoc.day12
import aoc.utils.*

object Problem2 extends Solver("12", 3509):
  def name = "Passage Pathing - Part 2"
  def solve(data: Vector[String]) = 
    given Vector[Cave] = data
    val connectedTo = data.caves.connections

    def allPaths(currentCave: Cave, visited: Set[Cave], path: Path): Set[Path] =
      val newVisited = if currentCave.isSmall then visited + currentCave else visited
      val possibleNewCaves =
        if path.filter(_.isSmall).exists(cave => path.count(_ == cave) == 2) then Set.empty[Cave]
        else connectedTo(currentCave) - "start"

      if currentCave == "end" then Set(path)
      else connectedTo(currentCave)
        .diff(visited)
        .union(possibleNewCaves)
        .flatMap(nextCave => allPaths(nextCave, newVisited, path :+ nextCave))

    allPaths("start", Set.empty, Vector("start")).size