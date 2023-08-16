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

  lazy val top = (pos.y - range - 1, pos.x)
  def isInsideMap(p: Pos2D)(using size: Int) = 0 <= p.x && p.x <= size && 0 <= p.y && p.y <= size
  def isInsideRange(p: Pos2D) = (pos manhattan p) <= range

def parse(data: List[String]) = data.map {
  case s"Sensor at x=$x, y=$y: closest beacon is at x=${bx}, y=${by}" =>
    Sensor((y.toInt, x.toInt), (by.toInt, bx.toInt))
}

extension (p: Pos2D) 
  def step(using s: Sensor) = 
    import Cardinal.*
    if p.x == s.pos.x then 
      if p.y < s.pos.y 
        then p.move(SouthEast) 
        else p.move(NorthWest)
    else if p.y == s.pos.y then 
      if p.x > s.pos.x 
        then p.move(SouthWest) 
        else p.move(NorthEast)
    else if p.x > s.pos.x then
      if p.y < s.pos.y 
        then p.move(SouthEast)
        else p.move(SouthWest)
    else if p.y < s.pos.y
      then p.move(NorthEast)
      else p.move(NorthWest)