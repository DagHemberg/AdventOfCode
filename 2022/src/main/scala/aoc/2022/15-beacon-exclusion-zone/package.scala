package aoc.y2022.day15
import problemutils.*, extensions.*

case class Sensor(pos: Pos2D, beacon: Pos2D):
  val range = pos manhattan beacon

  // todo: math
  def spots(row: Int) = 
    val v = pos.y - row
    val h = range - v.abs
    val (a, b) = (pos.x - h, pos.x + h)
    (a to b).map(x => (row, x)).toSet

def parse(data: List[String]) = data.map {
  case s"Sensor at x=$x, y=$y: closest beacon is at x=${bx}, y=${by}" =>
    Sensor((y.toInt, x.toInt), (by.toInt, bx.toInt))
}
