package aoc.day11
import aoc.utils.*

case class Octopus(energy: Int):
  def blinking = energy == 10
  def blinked = energy == 0

extension [A](matrix: Matrix[A])
  def surrounding(ind: Index) = Vector
    .tabulate(3, 3)((r, c) => Index(ind.row + r - 1, ind.col + c - 1))
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

  def step = octopodes.stepOnce.stepRecursive
