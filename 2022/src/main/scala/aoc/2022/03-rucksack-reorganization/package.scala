package aoc.y2022.day03
import problemutils.*, extensions.*

val priority = 
  val lower = ('a' to 'z').zipWithIndex.map((c, p) => c -> (p + 1)).toMap
  val upper = ('A' to 'Z').zipWithIndex.map((c, p) => c -> (p + 27)).toMap
  lower ++ upper
