package aoc.y2020.day02
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(2, 2020)(2)(1):
  def name = "Password Philosophy - Part 2"
  def solve(data: List[String]) = 
    case class Policy(a: Int, b: Int, key: Char, pwd: String):
      def isValid = pwd(a - 1) == key ^ pwd(b - 1) == key
    data.map:
      case s"${a}-${b} ${key}: ${pwd}" => Policy(a.toInt, b.toInt, key.head, pwd)
    .count(_.isValid)
