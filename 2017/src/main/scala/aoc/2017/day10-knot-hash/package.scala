package aoc.y2017
import aoc.utils.*

package object day10:
  def hash(lengths: List[Int]) = 
    lengths.foldLeft(((0 until 256).toList, 0, 0)) {
      case ((list, pos, skip), length) =>
        val newPos =
          val ls = (pos until pos + length).map(_ % list.size)
          (ls zip ls.reverse).toMap withDefault identity
        (list.indices.map(newPos.apply).map(list.apply).toList,
        (pos + length + skip) % list.size,
        skip + 1)
    }.head
