package aoc.day11
import aoc.utils.*

object Problem1 extends Solver("11", 1656):
  def name = "Dumbo Octopus - Part 1"
  def solve(data: Vector[String]) =
    var octopodes = data.map(_.split("").toVector.map(nrg => Octopus(nrg.toInt))).toMatrix
    
    var totalBlinks = 0
    for i <- 1 to 100 do
      octopodes = octopodes.step
      println(octopodes.map(x => if x.energy == 0 then "." else x.energy))
      Thread.sleep(50)
      totalBlinks += octopodes.count(_.blinked)

    totalBlinks