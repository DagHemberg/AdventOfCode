package aoc.y2017.day01
import aoc.utils.*

object Part2 extends Problem("2017", "01", "2")(6):
  def name = "Inverse Captcha - Part 2"
  def solve(data: Seq[String]) = 
    val num = data.head
    val (first, second) = num.splitAt(num.size / 2)

    first.zipWith(second)((a, b) => if a == b then a.asDigit else 0).sum * 2
