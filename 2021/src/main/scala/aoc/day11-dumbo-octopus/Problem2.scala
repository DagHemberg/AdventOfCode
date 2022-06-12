package aoc.day11
import aoc.utils.*

object Problem2 extends Solver("11", 195):
  def name = "Dumbo Octopus - Part 2"
  def solve(data: Vector[String]) = 
    var octopodes = data.map(_.split("").toVector.map(nrg => Octopus(nrg.toInt))).toMatrix

    var found = false
    var step = 1
    while !found do
      octopodes = octopodes.step
      if octopodes.forall(_.blinked) then found = true
      else step += 1

    step
    