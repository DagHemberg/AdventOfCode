package aoc.y2017
import aoc.utils.*

package object day07:
  def getRoot(data: Seq[String]) = 
    val all = data.map {
      case s"$name $_" => name
    }

    val referenced = data.flatMap {
      case s"$name ($num) -> $ls" => ls.split(", ")
      case s"$name $_" => Array.empty[String]
    }

    (all diff referenced).head
