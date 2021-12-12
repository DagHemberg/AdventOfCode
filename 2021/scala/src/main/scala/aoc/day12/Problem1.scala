package aoc.day12
import aoc.utils.*

object Problem1 extends Solver("12", 226):
  def name = "Passage Pathing - Part 1"
  def solve(data: Vector[String]) = 
    given Vector[Cave] = data
    val connectedTo = data.caves.connections
    
    def allPaths(currentCave: Cave, visited: Set[Cave], path: Path): Set[Path] =       
      val newVisited = if currentCave.isSmall then visited + currentCave else visited
        
      if currentCave == "end" then Set(path)
      else connectedTo(currentCave)
        .diff(visited)
        .flatMap(nextCave => allPaths(nextCave, newVisited, path :+ nextCave))

    allPaths("start", Set.empty, Vector.empty).size