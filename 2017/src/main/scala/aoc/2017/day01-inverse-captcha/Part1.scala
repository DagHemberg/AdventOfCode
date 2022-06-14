package aoc.y2017.day01
import aoc.utils.*

object Part1 extends Problem("2017", "01", "1")(9):
  def name = "Inverse Captcha - Part 1"
  def solve(data: Seq[String]) = 
    val num = data.head
    (num :+ num.last)
      .map(_.asDigit)
      .sliding(2)
      .map(s => if s.head == s.last then s.head.toInt else 0)
      .sum
