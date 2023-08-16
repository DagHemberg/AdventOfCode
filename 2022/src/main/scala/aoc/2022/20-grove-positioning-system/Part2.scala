package aoc.y2022.day20
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(20, 2022)(2)(Skip):
  def name = "Grove Positioning System - Part 2"
  def solve(data: List[String]) = 
    val numbers: Seq[Vec2] = data.map(_.toInt).zipWithIndex
    given Size = data.size
    val ls = numbers.iterate(ns => ns.foldLeft(ns)(_ moveDigit _))(10).map(fst)
    val zero = ls.indexOf(0)

    List(1000, 2000, 3000).sumBy(zero.jump andThen ls.apply andThen (_ * 811589153L))