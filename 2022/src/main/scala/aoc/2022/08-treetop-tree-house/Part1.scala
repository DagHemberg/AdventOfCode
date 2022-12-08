package aoc.y2022.day08
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(8, 2022)(1)(21):
  def name = "Treetop Tree House - Part 1"
  def solve(data: List[String]) = 
    val mat = parse(data).zipWithIndex
    val inside = shed(mat)

    def visible(elem: Int, index: Pos2D) = 
      val row = mat.row(index.row)
      val col = mat.col(index.col)
      val List(left, right) = row.split(elem, index)
      val List(top, bottom) = col.split(elem, index)
 
      def valid(vec: Seq[(Int, Pos2D)]) = vec.forall((e, _) => e < elem)
      Seq(left, right, top, bottom).exists(valid)

    
    (2 * (mat.height + mat.width - 2)) + inside.count(visible)
