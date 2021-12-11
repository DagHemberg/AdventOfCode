import aoc.utils.*
import aoc.day11.*

val mat = Matrix(Vector("5483143223",
"2745854711",
"5264556173",
"6141336146",
"6357385478",
"4167524645",
"2176841721",
"6882881134",
"4846848554",
"5283751526").map(_.split("").toVector.map(nrg => Octopus(nrg.toInt))))

val ex = Matrix(Vector("11111",
"19991",
"19191",
"19991",
"11111").map(_.split("").toVector.map(nrg => Octopus(nrg.toInt))))

val m = ex.step

Octopus(9)
Octopus(10)

mat
mat.step
mat.step.step
mat.step.step.step

m.stepOnce.stepBlink

val row = 0
val col = 1

Vector
  .tabulate(3, 3)((i, j) => Index(row + i - 1, col + j - 1))
  .flatten
  .filter(_ != Index(row, col))
  .filterNot(m.indexOutsideBounds)
  .map(m.apply)