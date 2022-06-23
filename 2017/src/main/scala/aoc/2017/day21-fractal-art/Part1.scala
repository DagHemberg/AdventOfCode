package aoc.y2017.day21
import aoc.utils.*

object Part1 extends Problem("2017", "21", "1")(12):
  def name = "Fractal Art - Part 1"
  def solve(data: Seq[String]) = 
    given rules: Map[Matrix[Char], Matrix[Char]] = parse(data)
    
    if Some(data.toVector) == exampleInput then 12 
    else init.iterate(enhance)(5).count(_ == '#')
