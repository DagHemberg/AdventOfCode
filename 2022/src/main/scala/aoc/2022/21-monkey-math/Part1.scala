package aoc.y2022.day21
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(21, 2022)(1)(151L):
  def name = "Monkey Math - Part 1"
  def solve(data: List[String]) =     
    given Map[String, String] = parse(data)
    build("root").eval
