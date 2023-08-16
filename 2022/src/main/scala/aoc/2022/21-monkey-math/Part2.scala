package aoc.y2022.day21
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(21, 2022)(2)(301L):
  def name = "Monkey Math - Part 2"
  def solve(data: List[String]) = 
    given Map[String, String] = parse(data)
    given Boolean = true
    build("root").solve.eval
