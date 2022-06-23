package aoc.y2017.day22
import aoc.utils.*

object Part2 extends Problem("2017", "22", "2")(2511944):
  def name = "Sporifica Virus - Part 2"
  def solve(data: Seq[String]) = 
    val mat = parse(data)
    val grid = collectFrom(mat)
    val center = (mat.height / 2, mat.width / 2)

    var virus = Virus(Pos(center), Direction.Up, 0, grid)
    for i <- 1 to 10_000_000 do virus = virus.moveEvolved
    virus.infections
