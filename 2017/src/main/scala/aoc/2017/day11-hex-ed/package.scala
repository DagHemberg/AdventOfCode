package aoc.y2017
import aoc.utils.*

package object day11:
  val start = Pos3D(0, 0, 0)
  def move(dir: String) = dir match
    case "n" => (0, 1, -1)
    case "s" => (0, -1, 1)
    case "ne" => (1, 0, -1)
    case "sw" => (-1, -0, 1)
    case "nw" => (-1, 1, 0)
    case "se" => (1, -1, 0)
