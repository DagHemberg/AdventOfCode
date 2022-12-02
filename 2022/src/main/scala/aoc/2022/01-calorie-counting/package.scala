package aoc.y2022.day01
import problemutils.*, extensions.*

def parse(data: List[String]) = 
  data
    .mkString("\n")
    .split("\n\n").toList
    .map(_.split("\n").map(_.toInt).sum)
