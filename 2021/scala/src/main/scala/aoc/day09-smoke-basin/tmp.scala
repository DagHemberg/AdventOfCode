package aoc.day09
import aoc.utils.{Matrix, Index}

extension (m: Matrix[Int])
  def surrounding(i: Index): Vector[Index] = 
    val (up, down, left, right) = ((i.row - 1, i.col), (i.row + 1, i.col), (i.row, i.col - 1), (i.row, i.col + 1))
    Vector(up, down, left, right).filter(!m.indexOutsideBounds(_, _)).map(Index.apply)
