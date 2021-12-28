package aoc.day09
import aoc.utils.*

extension (m: Matrix[Int])
  def surrounding(i: Index): Vector[Index] = 
    Vector(i.up, i.down, i.left, i.right).filterNot(m.indexOutsideBounds)
