package aoc.y2022.day03
import problemutils.*, extensions.*

val priority = 
  (('a' to 'z') ++ ('A' to 'Z'))
    .zipWithIndex
    .toMap
    .view.mapValues(_ + 1)