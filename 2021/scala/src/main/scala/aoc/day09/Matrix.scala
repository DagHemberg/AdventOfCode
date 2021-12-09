package aoc.day09

case class Index(row: Int, col: Int)

case class Matrix(input: Vector[Vector[Int]]):
  override def toString = input.map(_.mkString(" ")).mkString("\n")

  def apply(row: Int, col: Int): Int = input(row)(col)
  def apply(index: Index): Int = input(index.row)(index.col)

  private def indexOutside(row: Int, col: Int) =
    row < 0 || row >= height || col < 0 || col >= width

  val height = input.size
  val width = input.head.size

  def col(col: Int) = input.map(_(col))
  def row(row: Int) = input(row)

  def slice(row: Int, col: Int)(width: Int, height: Int) = Matrix(
    input.slice(row, row + width).map(_.slice(col, col + height))
  )

  def indices = (0 until height).flatMap(row => (0 until width).map(col => Index(row, col))).toVector

  def map(f: Int => Int): Matrix = Matrix(input.map(_.map(f)))

  def surround(row: Int, col: Int) = 
    val (up, down, left, right) = ((row - 1, col), (row + 1, col), (row, col - 1), (row, col + 1))
    Vector(up, down, left, right).filter(!indexOutside(_, _)).map(Index.apply)

  // def surround(row: Int, col: Int): Matrix =
  //   slice(row - 1, col - 1)(3, 3)

  def size = (input.size, input.head.size)