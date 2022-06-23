package aoc.y2017
import aoc.utils.*

package object day21:
  val init = parseMatrix(".#./..#/###")

  def parseMatrix(str: String) =
    str.split("/").toVector.map(_.toVector).toMatrix
  
  def parse(data: Seq[String]) = data
    .map { case s"$in => $out" => parseMatrix(in) -> parseMatrix(out) }
    .toMap
    .flatMap((k, v) => rotations(k).map(k2 => k2 -> v))

  def rotations(mat: Matrix[Char]) = Set(
    mat,
    mat.flipCols,
    mat.rotateRight,
    mat.rotateRight.flipRows,
    mat.rotateLeft,
    mat.rotateLeft.flipRows,
    mat.flipRows,
    mat.flipRows.flipCols
  )

  def split(mat: Matrix[Char]) = 
    def partition(s: Int, n: Int) = Matrix(s / n, s / n)((r, c) => mat.slice(r * n, c * n)(n, n))
    val s = mat.height
    (s % 2, s % 3) match
      case (0, _) => partition(s, 2)      
      case (_, 0) => partition(s, 3)

  def enhance(mat: Matrix[Char])(using rules: Map[Matrix[Char], Matrix[Char]]) =
    split(mat).map(rules).flatten
