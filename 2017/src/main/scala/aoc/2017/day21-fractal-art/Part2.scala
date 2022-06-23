package aoc.y2017.day21
import aoc.utils.*

object Part2 extends Problem("2017", "21", "2")(12):
  def name = "Fractal Art - Part 2"
  def solve(data: Seq[String]) = 
    given rules: Map[Matrix[Char], Matrix[Char]] = parse(data)
    
    if Some(data.toVector) == exampleInput then 12 
    else init.iterate(enhance)(18).count(_ == '#')
