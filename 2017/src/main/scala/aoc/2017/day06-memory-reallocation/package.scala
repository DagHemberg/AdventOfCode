package aoc.y2017
import aoc.utils.*
import collection.mutable as mutable

package object day06:
  def count(banks: => mutable.ArraySeq[Int]) = 
    val seen = mutable.ArrayBuffer.empty[mutable.ArraySeq[Int]]

    while !seen.contains(banks) do
      seen += banks.clone
      val max = banks.max
      val index = banks.indexOf(max)
      banks(index) = 0
      for i <- 0 until max do banks((index + i + 1) % banks.size) += 1

    seen