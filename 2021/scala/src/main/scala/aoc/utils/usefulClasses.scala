package aoc.utils

case class Pos(x: Int, y: Int)
case class Line(start: Pos, end: Pos)
case class Index(row: Int, col: Int) // functionally equivalent to Pos