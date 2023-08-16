package aoc.y2020.day01
import problemutils.*, extensions.*

def calculate(data: List[String], noCombinations: Int) = data
  .map(_.toLong)
  .combinations(noCombinations)
  .find(_.sum == 2020)
  .map(_.product)
  .getOrElse(-1l)
