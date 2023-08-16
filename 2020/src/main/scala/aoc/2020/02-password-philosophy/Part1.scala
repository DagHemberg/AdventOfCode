package aoc.y2020.day02
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(2, 2020)(1)(2):
  def name = "Password Philosophy - Part 1"
  def solve(data: List[String]) = 
    case class Policy(min: Int, max: Int, char: Char, password: String):
      def isValid = password.count(_ == char) >= min && password.count(_ == char) <= max
    
    data
      .map:
        case s"${min}-${max} ${char}: ${password}" => 
          Policy(min.toInt, max.toInt, char.head, password)
      .count(_.isValid)
