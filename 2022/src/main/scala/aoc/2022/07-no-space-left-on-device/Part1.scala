package aoc.y2022.day07
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(7, 2022)(1)(95437L):
  def name = "No Space Left On Device - Part 1"
  def solve(data: List[String]) = 
    parse(data).values.filter(_ < 100000).sum
