package aoc.y2017.day12
import aoc.utils.*

object Part2 extends Problem("2017", "12", "2")(2):
  def name = "Digital Plumber - Part 2"
  def solve(data: Seq[String]) = 
    val adjacents = parse(data)
    
    adjacents
      .keySet
      .foldLeft ((0, Set.empty[String])) { case ((groups, seen), next) =>
        if seen(next) then (groups, seen)
        else (groups + 1, seen ++ Set(next).converge(set => set ++ (set flatMap adjacents)))
      }.head