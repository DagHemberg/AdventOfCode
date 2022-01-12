package aoc.utils
import math.Numeric.Implicits.infixNumericOps

case class Index(row: Int, col: Int):
  override def toString: String = s"($row, $col)"
  def up = Index(row - 1, col)
  def down = Index(row + 1, col)
  def left = Index(row, col - 1)
  def right = Index(row, col + 1)

extension [A: Numeric](xs: Vector[A])
  def *(mat: Matrix[A]): Vector[A] = (Vector(xs).transpose.toMatrix * mat).toVector.flatten

extension [A](vss: Vector[Vector[A]]) 
  def toMatrix = Matrix(vss)

extension [A: Numeric](mat: Matrix[A])
  def sum = mat.toVector.flatten.sum
  def product = mat.toVector.flatten.product
  def +(other: Matrix[A]) = mat zip other map ((a, b) => a + b)
  def -(other: Matrix[A]) = mat zip other map ((a, b) => a - b)
  def *(other: Matrix[A]): Matrix[A] = 
    require(mat.width == other.height)
    Matrix(mat.height, other.width)((row, col) => mat.row(row) dot other.col(col))

  def *(vec: Vector[A]): Vector[A] = (mat * Vector(vec).transpose.toMatrix).toVector.flatten

  def determinant: A =     
    require(mat.width == mat.height)
    if (mat.width == 1) mat.toVector.flatten.head
    else if (mat.width == 2) (mat.row(0)(0) * mat.row(1)(1) - mat.row(0)(1) * mat.row(1)(0))
    else (0 until mat.width)
      .map(i => (if i % 2 == 0 then mat.col(i)(0) else -mat.col(i)(0)) * mat.dropCol(i).dropRow(0).determinant)
      .sum

extension [A](mat: Matrix[Matrix[A]])
  def flatten = mat.toVector
    .map(_.reduce((acc, curr) => acc.appendedRight(curr)))
    .reduce((acc, curr) => acc.appendedBottom(curr))

case class Matrix[A](input: Vector[Vector[A]]):
  require(input.size > 0, "Matrix must have at least one row")
  require(input.head.size > 0, "Matrix must have at least one column")
  require(input.forall(_.size == input.head.size), "All rows must have the same length")

  val height = input.size
  val width = input.head.size

  override def toString = s"\n${input.map(_.mkString(" ")).mkString("\n")}"

  def apply(row: Int, col: Int): A = input(row)(col)
  def apply(index: Index): A = input(index.row)(index.col)

  def row(row: Int) = input(row).toVector
  def col(col: Int) = input.map(_(col)).toVector
  
  def toVector = input.map(_.toVector).toVector
  def rows = toVector
  def cols = toVector.transpose

  def indexOutsideBounds(row: Int, col: Int): Boolean =
    row < 0 || row >= height || col < 0 || col >= width
  def indexOutsideBounds(index: Index): Boolean = 
    indexOutsideBounds(index.row, index.col)

  def map[B](f: A => B) = input.map(_.map(f)).toMatrix
  def forEach(f: A => Unit) = input.foreach(_.foreach(f))
  def count(f: A => Boolean) = input.map(_.count(f)).sum
  def forall(f: A => Boolean) = input.forall(_.forall(f))
  def exists(f: A => Boolean) = input.exists(_.exists(f))

  def slice(row: Int, col: Int)(width: Int, height: Int): Matrix[A] = 
    input.slice(row, row + width).map(_.slice(col, col + height)).toMatrix
  def slice(index: Index)(width: Int, height: Int): Matrix[A] = 
    slice(index.row, index.col)(width, height)

  def transpose = input.transpose.toMatrix // ??? since when is this a thing
  def flipCols = input.map(_.reverse).toMatrix
  def flipRows = input.reverse.toMatrix

  def appendedLeft(other: Matrix[A]): Matrix[A] = 
    require(other.height == height, "Can't append matrices of different heights to the side")
    Matrix(input.zip(other.input).map((row, otherRow) => otherRow ++ row))

  def appendedRight(other: Matrix[A]): Matrix[A] =
    require(other.height == height, "Can't append matrices of different heights to the side")
    Matrix(input.zip(other.input).map((row, otherRow) => row ++ otherRow))

  def appendedTop(other: Matrix[A]): Matrix[A] =
    require(other.width == width, "Can't append matrices of different widths to the top")
    Matrix(other.input ++ input)

  def appendedBottom(other: Matrix[A]): Matrix[A] =
    require(other.width == width, "Can't append matrices of different widths to the bottom")
    Matrix(input ++ other.input)

  def dropRow(row: Int) = (input.take(row) ++ input.drop(row + 1)).toMatrix
  def dropCol(col: Int) = input.map(row => row.take(col) ++ row.drop(col + 1)).toMatrix

  def zip[B](other: Matrix[B]): Matrix[(A, B)] =
    require(size == other.size, "Can't zip matrices of different dimensions")
    Matrix(input.zip(other.input).map((row, otherRow) => row.zip(otherRow)))

  def indices: Matrix[Index] = 
    (0 until height).toVector.map(row => (0 until width).toVector.map(col => Index(row, col))).toMatrix

  def size = (input.size, input.head.size)

object Matrix:
  def apply[A](height: Int, width: Int)(f: (Int, Int) => A): Matrix[A] = 
    Matrix((0 until height).toVector.map(row => (0 until width).toVector.map(col => f(row, col))))

  def identity(size: Int): Matrix[Int] = 
    Matrix(size, size)((row, col) => if row == col then 1 else 0)

  private def sinD(x: Double) = math.sin(math.toRadians(x))
  private def cosD(x: Double) = math.cos(math.toRadians(x))

  def rotate2D(rad: Double) = Vector(
      Vector(cosD(rad), -sinD(rad)), 
      Vector(sinD(rad), cosD(rad))
    ).toMatrix

  def rotate3DX(a: Double) = Vector(
    Vector(1.0, 0.0,      0.0),
    Vector(0,   cosD(a),  -sinD(a)),
    Vector(0,   sinD(a),  cosD(a))
  ).toMatrix

  def rotate3DY(a: Double) = Vector(
    Vector(cosD(a),  0,   sinD(a)),
    Vector(0.0,      1.0, 0.0),
    Vector(-sinD(a), 0,   cosD(a))
  ).toMatrix

  def rotate3DZ(a: Double) = Vector(
    Vector(cosD(a), -sinD(a), 0),
    Vector(sinD(a), cosD(a),  0),
    Vector(0.0,     0.0,      1.0)
  ).toMatrix