package aoc.utils

 import math.Numeric.Implicits.infixNumericOps

extension [A](vss: IndexedSeq[IndexedSeq[A]]) 
  def toMatrix = Matrix(vss)

extension [A: Numeric](mat: Matrix[A])
  def sum = mat.toVector.flatten.sum
  def product = mat.toVector.flatten.product
  // todo: matrix multiplication and addition / subtraction
  // todo: determinant

case class Matrix[A](input: IndexedSeq[IndexedSeq[A]]):
  require(input.size > 0, "Matrix must have at least one row")
  require(input.head.size > 0, "Matrix must have at least one column")
  require(input.forall(_.size == input.head.size), "All rows must have the same length")

  val height = input.size
  val width = input.head.size

  override def toString = "\n" + input.map(_.mkString(" ")).mkString("\n")

  def col(col: Int) = input.map(_(col))
  def row(row: Int) = input(row)

  def apply(row: Int, col: Int): A = input(row)(col)
  def apply(index: Index): A = input(index.row)(index.col)

  def toVector = input.map(_.toVector).toVector

  def indexOutsideBounds(row: Int, col: Int): Boolean =
    row < 0 || row >= height || col < 0 || col >= width
  def indexOutsideBounds(index: Index): Boolean = 
    indexOutsideBounds(index.row, index.col)

  // nothing that changes the size of the matrix because idk how to handle that
  def map[B](f: A => B) = Matrix(input.map(_.map(f)))
  def forEach(f: A => Unit) = input.foreach(_.foreach(f))
  def count(f: A => Boolean) = input.map(_.count(f)).sum
  def forall(f: A => Boolean) = input.forall(_.forall(f))
  def exists(f: A => Boolean) = input.exists(_.exists(f))

  def slice(row: Int, col: Int)(width: Int, height: Int): Matrix[A] = 
    input.slice(row, row + width).map(_.slice(col, col + height)).toMatrix
  def slice(index: Index)(width: Int, height: Int): Matrix[A] = 
    slice(index.row, index.col)(width, height)

  def transpose = input.transpose.toMatrix // ??? since when is this a thing

  def indices: Matrix[Index] = 
    (0 until height).map(row => (0 until width).map(col => Index(row, col))).toMatrix

  def size = (input.size, input.head.size)
