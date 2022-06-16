package aoc.y2017
import aoc.utils.*

package object day10:
  def hash(lengths: List[Int]) = 
    lengths.foldLeft(((0 until 256).toList, 0, 0)) { case ((prevList, pos, skip), length) =>
      val newIndices = 
        val circled = (pos until pos + length).map(_ % prevList.size)
        (circled zip circled.reverse).toMap.withDefault(identity)

      val newList = prevList
        .indices
        .map(newIndices.apply)
        .map(prevList.apply)
        .toList

      (newList, (pos + length + skip) % prevList.size, skip + 1)
    }.head
