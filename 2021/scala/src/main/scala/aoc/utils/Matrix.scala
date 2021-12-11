package aoc.day09

case class Index(row: Int, col: Int)

case class Matrix[A](input: Vector[Vector[A]]):
  override def toString = input.map(_.mkString(" ")).mkString("\n")

  def apply(row: Int, col: Int): A = input(row)(col)
  def apply(index: Index): A = input(index.row)(index.col)

  def indexOutsideBounds(row: Int, col: Int) =
    row < 0 || row >= height || col < 0 || col >= width

  val height = input.size
  val width = input.head.size

  def col(col: Int) = input.map(_(col))
  def row(row: Int) = input(row)

  def slice(row: Int, col: Int)(width: Int, height: Int) = Matrix(
    input.slice(row, row + width).map(_.slice(col, col + height))
  )

  def indices = (0 until height).flatMap(row => (0 until width).map(col => Index(row, col))).toVector

  def map[B](f: A => B) = Matrix(input.map(_.map(f)))
  def forEach(f: A => Unit) = input.foreach(_.foreach(f))

  def size = (input.size, input.head.size)