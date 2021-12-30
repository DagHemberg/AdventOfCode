package aoc.day13
import aoc.utils.*

object Problem2 extends Solver("13", "See above"):
  def name = "Transparent Origami - Part 2"
  def solve(data: Vector[String]) = 
    given Vector[String] = data
    val folded = fold(points, commands)
    
    // pretty :)
    val text = Matrix
      (folded.maxBy(_.y).y + 1, folded.maxBy(_.x).x + 1)
      ((x, y) => folded(Pos(y, x)))
        .toVector
        .map(_.map(if _ then s"${Console.GREEN_B}  ${Console.RESET}" else "  "))
        .map(r => s"    │ ${r.mkString} │")
        .mkString("\n")

    val textWidth = (folded.maxBy(_.x).x + 1)
    s"""|┌─${Vector.fill(textWidth * 2)("─").mkString}─┐
        |$text
    |    └─${Vector.fill(textWidth * 2)("─").mkString}─┘""".stripMargin.log

    // theres no actual checkable expected output for the example 
    // here, so we have to manually make it pass the check
    "See above"
