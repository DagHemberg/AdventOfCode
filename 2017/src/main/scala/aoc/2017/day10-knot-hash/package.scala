package aoc.y2017
import aoc.utils.*

package object day10:
  private val indices = (0 until 256).toList

  def twist(lengths: Seq[Int]) =     
    lengths.foldLeft((indices, 0, 0)) { case ((prevList, pos, skip), length) =>
      val newIndices = 
        val circled = (pos until pos + length).map(_ % 256)
        (circled zip circled.reverse).toMap.withDefault(identity)

      val newList = indices.map(newIndices.apply andThen prevList.apply)
      (newList, (pos + length + skip) % 256, skip + 1)
    }.head

  def hash(str: String) = 
    val lengths = str.map(_.toInt).toList ++ List(17, 31, 73, 47, 23)
    val many = Vector.fill(64)(lengths).flatten
    
    twist(many)
      .sliding(16, 16)
      .map(_.reduce(_ ^ _).toHexString.padLeftTo(2, '0'))
      .mkString