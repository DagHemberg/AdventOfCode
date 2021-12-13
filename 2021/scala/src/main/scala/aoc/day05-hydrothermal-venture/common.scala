package aoc.day05
import aoc.utils.*

extension (l: Line)
  def range(st: Int, en: Int): Vector[Int] =
    (st to en by (if st < en then 1 else -1)).toVector

  def xs: Vector[Int] = range(l.start.x, l.end.x)
  def ys: Vector[Int] = range(l.start.y, l.end.y)