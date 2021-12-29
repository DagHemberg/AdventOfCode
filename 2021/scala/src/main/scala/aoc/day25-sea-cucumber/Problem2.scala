package aoc.day25
import aoc.utils.*

object Problem2 extends Solver("25", "Thanks for checking out my program!"):
  def name = "Sea Cucumber - Part 2"

  def solve(data: Vector[String]) = if data == testInput 
    then "Thanks for checking out my program!"
    else "This was my first year trying out AoC and it's been a lot of fun, hopefully you could read my awful code lmao"
