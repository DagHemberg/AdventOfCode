package aoc.day09
import aoc.utils.{Matrix, Index}

extension (m: Matrix[Int])
  def surrounding(row: Int, col: Int) = 
    val (up, down, left, right) = ((row - 1, col), (row + 1, col), (row, col - 1), (row, col + 1))
    Vector(up, down, left, right).filter(!m.indexOutsideBounds(_, _)).map(Index.apply)
