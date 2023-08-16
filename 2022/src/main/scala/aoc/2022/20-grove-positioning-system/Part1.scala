package aoc.y2022.day20
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(20, 2022)(1)(Skip):
  def name = "Grove Positioning System - Part 1"
  def solve(data: List[String]) = 
    val numbers: Seq[Vec2] = data.map(_.toInt).zipWithIndex
    given Size = data.size
    val ls = numbers.foldLeft(numbers)(_ moveDigit _).map(fst)
    val zero = ls.indexOf(0)

    List(1000, 2000, 3000)
      .sumBy(subOne andThen zero.jump andThen ls.apply)
