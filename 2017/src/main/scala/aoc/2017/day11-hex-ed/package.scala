package aoc.y2017
import aoc.utils.*

package object day11:
  val start = (0, 0, 0)
  def parse(dir: String) = 
    import Hex.*
    dir match
      case "n" => North
      case "s" => South
      case "ne" => NorthEast
      case "sw" => SouthWest
      case "nw" => NorthWest
      case "se" => SouthEast