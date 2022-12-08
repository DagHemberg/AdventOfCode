package aoc.y2022.day07
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(7, 2022)(2)(24933642L):
  def name = "No Space Left On Device - Part 2"
  def solve(data: List[String]) = 
    val dirs = parse(data)
    val total = dirs(List("/"))
    
    dirs.values
      .filter(total - _ <= 40_000_000)
      .min
