package aoc.utils

case class Pos(x: Int, y: Int):
  def transpose = Pos(y, x)
  def tuple = (x, y)
case class Line(start: Pos, end: Pos)
case class Index(row: Int, col: Int) // functionally equivalent to Pos

case class TimedEval[A](duration: Double, result: A)
object TimedEval:
  def time[A](block: => A): TimedEval[A] =
    val start = System.nanoTime()
    val result = block
    val duration = (System.nanoTime() - start) / 1E9
    TimedEval(duration, result)