package aoc.day09
import aoc.utils.*

extension (m: Matrix[Int])
  def surrounding(row: Int, col: Int) = 
    val (up, down, left, right) = ((row - 1, col), (row + 1, col), (row, col - 1), (row, col + 1))
    Vector(up, down, left, right).filter(!m.indexOutsideBounds(_, _)).map(Index.apply)

object Problem1 extends Solver("09", 15):
  def name = "Smoke Basin - Part 1"
  def solve(data: Vector[String]) =
    val matrix = Matrix(data.map(_.split("").map(_.toInt).toVector))

    val dangerLevels = matrix.indices
      .map(i => (matrix surrounding (i.row, i.col), i))
      .filter((surrounding, index) => surrounding forall (matrix(index) < matrix(_)))
      .map((_, pos) => matrix(pos) + 1)

    dangerLevels.sum
      