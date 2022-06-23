package aoc.y2017.day22
import aoc.utils.*

object Part1 extends Problem("2017", "22", "1")(5587):
  def name = "Sporifica Virus - Part 1"
  def solve(data: Seq[String]) = 
    val mat = parse(data)
    val grid = collectFrom(mat)
    val center = (mat.height / 2, mat.width / 2)

    Virus(Pos(center), Direction.Up, 0, grid)
      .iterate(_.move)(10_000)
      .infections
