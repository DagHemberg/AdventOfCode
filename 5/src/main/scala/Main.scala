package aoc.day05

case class Pos(x: Int, y: Int)

case class Line(start: Pos, end: Pos):

  val xRange = (start.x min end.x) to (start.x max end.x)
  val yRange = (start.y min end.y) to (start.y max end.y)

  def isDiagonal = (start.x - end.x).abs == (start.y - end.y).abs

  def inside =
    if      start.x == end.x then yRange.map(y => Pos(start.x, y)).toVector
    else if start.y == end.y then xRange.map(x => Pos(x, start.y)).toVector
    else if isDiagonal then
      val xs = if start.x < end.x then xRange else xRange.reverse
      val ys = if start.y < end.y then yRange else yRange.reverse
      xs.zip(ys).map(Pos.apply)
    else Vector.empty[Pos]

@main
def run =

  // a thing of beauty
  val lines = scala.io.Source
    .fromFile("input.txt").getLines.toVector
    .map(_.split(" -> ").map(_.split(",").map(_.toInt)).map(xs => Pos(xs(0), xs(1)))).map(xs => Line(xs(0), xs(1)))

  val allPositions = lines.flatMap(_.inside)
  val intersections = allPositions.groupBy(identity).filter(_._2.size > 1).keys.toVector
  
  // for debugging purposes
  // val field = Vector
  //   .tabulate(allPositions.maxBy(_.y).y + 1, allPositions.maxBy(_.x).x + 1)(
  //     (y, x) =>
  //       if allPositions.contains(Pos(x, y)) then
  //         allPositions.count(_ == Pos(x, y))
  //       else "."
  //   )
  //   .map(_.mkString)
  //   .mkString("\n")

  // println(field)

  println(allPositions.size)
  println(intersections.size)
