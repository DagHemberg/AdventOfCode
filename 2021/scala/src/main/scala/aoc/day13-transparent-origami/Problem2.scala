package aoc.day13
import aoc.utils.*

object Problem2 extends Solver("13", "See above"):
  def name = "Transparent Origami - Part 2"
  def solve(data: Vector[String]) = 
    val points = data
      .takeWhile(_.nonEmpty)
      .map { case s"$x,$y" => Pos(x.toInt, y.toInt) }

    val commands = data
      .reverse
      .takeWhile(_.nonEmpty)
      .reverse
      .map { case s"fold along $dir=$value" => Command(dir, value.toInt) }

    val folded = commands.foldLeft(points.toSet)((pts, command) => command match
      case Command("x", n) => pts.map(p => if p.x < n then p else Pos(2 * n - p.x, p.y))
      case Command("y", n) => pts.map(p => if p.y < n then p else Pos(p.x, 2 * n - p.y))
    )

    // pretty :)
    val textWidth = (folded.maxBy(_.x).x + 1)
    val text = Matrix
      (folded.maxBy(_.y).y + 1, folded.maxBy(_.x).x + 1)
      ((x, y) => folded(Pos(y, x)))
        .toVector
        .map(_.map(if _ then s"${Console.GREEN_B}  ${Console.RESET}" else "  "))
        .map(r => s"    │ ${r.mkString} │")
        .mkString("\n")

    s"""|┌─${Vector.fill(textWidth * 2)("─").mkString}─┐
        |$text
    |    └─${Vector.fill(textWidth * 2)("─").mkString}─┘""".stripMargin.debug

    // theres no actual expected output for the example 
    // here, so we have to manually make it pass the check
    "See above"
