package aoc.day05

case class Pos(x: Int, y: Int)

case class Line(start: Pos, end: Pos):
  def inside =
    if start.x == end.x then
      ((start.y min end.y) to (start.y max end.y)).map(y => Pos(start.x, y)).toVector
    else if start.y == end.y then
      ((start.x min end.x) to (start.x max end.x)).map(x => Pos(x, start.y)).toVector
    else
      // todo?
      Vector.empty[Pos]

@main
def run =

  // a thing of beauty
  val lines = scala.io.Source
    .fromFile("input.txt").getLines.toVector
    .map(_.split(" -> ").map(_.split(",").map(_.toInt)).map(xs => Pos(xs(0), xs(1)))).map(xs => Line(xs(0), xs(1)))

  val allPositions = lines.flatMap(_.inside)
  val intersections = allPositions.groupBy(identity).filter(_._2.size > 1).keys.toVector

  println(allPositions.size)
  println(intersections.size)
