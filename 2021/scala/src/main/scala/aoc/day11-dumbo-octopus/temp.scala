package aoc.day11
import aoc.utils.{Matrix, Index}

case class Octopus(energy: Int):
  def blinking = energy == 10
  def blinked = energy == 0
  override def toString = if blinking || blinked then "X" else energy.toString

extension [A](matrix: Matrix[A])
  def surrounding(ind: Index) = Vector
    .tabulate(3, 3)((i, j) => Index(ind.row + i - 1, ind.col + j - 1))
    .flatten
    .filter(_ != Index(ind.row, ind.col))
    .filterNot(matrix.indexOutsideBounds)
    .map(matrix.apply)

extension (octopodes: Matrix[Octopus])
  def stepOnce =
    octopodes.map(octopus => Octopus(octopus.energy + 1))

  def stepBlink = octopodes.indices.map(index =>
    val oct = octopodes(index)
    if oct.blinking || oct.blinked then Octopus(0)
    else
      val tot = octopodes(index).energy + (octopodes surrounding index).count(_.blinking)
      Octopus(if tot > 10 then 10 else tot)
  )

  def stepRecursive: Matrix[Octopus] =
    if octopodes == octopodes.stepBlink then octopodes
    else octopodes.stepBlink.stepRecursive

  // def step(n: Int) = (1 to n).foldLeft(octopodes)((octs, _) => octs.stepOnce.stepRecur)
  def step = octopodes.stepOnce.stepRecursive