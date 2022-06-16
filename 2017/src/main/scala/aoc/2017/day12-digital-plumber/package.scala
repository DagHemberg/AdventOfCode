package aoc.y2017
import aoc.utils.*

package object day12:
  def parse(data: Seq[String]) = data.map { 
    case s"$num <-> $ls" => num -> ls.split(", ").toSet 
  }.toMap