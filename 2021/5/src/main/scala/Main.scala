package aoc.day05

case class Pos(x: Int, y: Int)

case class Line(start: Pos, end: Pos):

  private def range(st: Int, en: Int): Vector[Int] = (st to en by (if st < en then 1 else -1)).toVector
  private val (xs, ys) = (range(start.x, end.x), range(start.y, end.y))

  def isDiagonal = (start.x - end.x).abs == (start.y - end.y).abs

  def positions =
    if      start.x == end.x then ys.map(y => Pos(start.x, y)).toVector 
    else if start.y == end.y then xs.map(x => Pos(x, start.y)).toVector 
    else if isDiagonal       then xs zip ys map Pos.apply // comment out this line to get solution for task 1
    else                          Vector.empty[Pos]

@main
def run =

  // a thing of beauty
  val lines = scala.io.Source
    .fromFile("input.txt").getLines.toVector
    .map(_.split(" -> ").map(_.split(",").map(_.toInt)).map(c => Pos(c(0), c(1)))).map(p => Line(p(0), p(1)))

  val allPositions = lines.flatMap(_.positions)
  val intersections = allPositions.groupBy(identity).filter((id, coll) => coll.size > 1).keys.toVector

  println(intersections.size)
