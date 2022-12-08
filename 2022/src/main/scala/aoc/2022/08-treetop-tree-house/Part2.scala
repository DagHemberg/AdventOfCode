package aoc.y2022.day08
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(8, 2022)(2)(8):
  def name = "Treetop Tree House - Part 2"
  def solve(data: List[String]) = 
    val mat = parse(data).zipWithIndex
    val inside = shed(mat)

    def scenicScore(elem: Int, index: Pos2D) = 
      val row = mat.row(index.row)
      val col = mat.col(index.col)
      val List(left, right) = row.split(elem, index)
      val List(up, down) = col.split(elem, index)

      extension (vec: Seq[(Int, Pos2D)]) 
        def score = 
          val taken = vec.takeWhile((e, _) => e < elem)
          if taken == vec then vec.size else taken.size + 1

      left.reverse.score * right.score * up.reverse.score * down.score

    inside.map(scenicScore).toVector.max