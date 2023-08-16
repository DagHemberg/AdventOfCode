package aoc.y2022.day25
import problemutils.*, extensions.*

case class Snafu(str: String):
  import Snafu.*
  override def toString = str

  def +(other: Snafu) = 
    val (a, b) = 
      if str.length > other.str.length 
      then ('0' +: str, other.str.padLeftTo(str.length + 1, '0'))
      else ('0' +: other.str, str.padLeftTo(other.str.length + 1, '0'))

    var carry = 0
    val arr = Array.fill(a.length)(0)
    val xs = a.map(convert).zip(b.map(convert)).zipWithIndex.reverse

    for ((t1, t2), i) <- xs do
      val next = math.floorMod(t1 + t2 + carry + 2, 5) - 2
      carry = if (t1 + t2 + carry).abs <= 2 then 0 else -next.sign
      arr(i) = next

    Snafu(arr.map(invert).mkString.dropWhile(_ == '0'))

object Snafu:
  val convert = Map(
    '=' -> -2,
    '-' -> -1,
    '0' -> 0,
    '1' -> 1,
    '2' -> 2
  )

  val invert = convert.map(_.swap)
