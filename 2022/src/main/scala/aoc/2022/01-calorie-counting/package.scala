package aoc.y2022.day01
import problemutils.*, extensions.*

def parse(data: List[String]) = 
  data.split("").map(_.sumBy(_.toInt))
