package aoc.y2017
import aoc.utils.*

package object day13:
  def parse(data: Seq[String]) = data.map { case s"$pos: $range" =>
    pos.toInt -> range.toInt
  }.toMap

  def catches(depth: Int, range: Int)(using delay: Int = 0) =
    (depth + delay) % (2 * range - 2) == 0
