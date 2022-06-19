package aoc.y2017
import aoc.utils.*

package object day14:
  import day10.hash
  def defrag(key: String) = 
    (0 until 128).map(n => BigInt(hash(s"$key-$n"), 16)
      .toString(2)
      .padLeftTo(128, '0')
      .toSeq
    ).toMatrix
