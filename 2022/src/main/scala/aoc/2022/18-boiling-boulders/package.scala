package aoc.y2022.day18
import problemutils.*, extensions.*

def parse(data: List[String]): Set[Pos3D] = data.map {
  case s"$x,$y,$z" => (x.toInt, y.toInt, z.toInt)
}.toSet

extension (p: Pos3D)
  def adjacent = List(
    p + (1,  0,  0),
    p + (-1, 0,  0),
    p + (0,  1,  0),
    p + (0, -1,  0),
    p + (0,  0,  1),
    p + (0,  0, -1)
  )
