package aoc.day13
import aoc.utils.*

object Problem1 extends Solver("13", 17):
  def name = "Transparent Origami - Part 1"
  def solve(data: Vector[String]) =
    val points = data
      .takeWhile(_.nonEmpty)
      .map { case s"$x,$y" => Pos(x.toInt, y.toInt) }

    val commands = data.reverse
      .takeWhile(_.nonEmpty)
      .reverse
      .map { case s"fold along $dir=$value" => Command(dir, value.toInt) }

    val paper =
      Matrix(points.maxBy(_.y).y + 1, points.maxBy(_.x).x + 1)((x, y) =>
        points.find(_ == Pos(y, x)).isDefined
      )

    // this takes 15 seconds to run
    // i knew i wasnt writing optimal code but jesus
    val newPaper = commands.head match
      case Command("y", n) =>
        val top = paper.slice(0, 0)(n, paper.width)
        val bottom = paper.slice(n + 1, 0)(paper.height, paper.width).flipRows
        if top.height == bottom.height then
          top zip bottom map ((top, btm) => top || btm)
        else
          val shorter = if top.height < bottom.height then top else bottom
          val taller = if top.height > bottom.height then top else bottom
          val lessShort = shorter.appendedTop(Matrix
            (taller.height - shorter.height, shorter.width)
            ((row, col) => false)
          )
          lessShort zip taller map ((short, tall) => short || tall)
      case Command("x", n) =>
        val right = paper.slice(0, n + 1)(paper.height, paper.width)
        val left = paper.slice(0, 0)(paper.height, n).flipCols
        if left.width == right.width then
          left zip right map ((left, right) => left || right)
        else
          val thinner = if left.width < right.width then left else right
          val wider = if left.width > right.width then left else right
          val lessThin = thinner.appendedRight(Matrix
            (thinner.height, wider.width - thinner.width)
            ((row, col) => false)
          )
          lessThin zip wider map ((thin, wide) => thin || wide)

    newPaper.count(_ == true)
