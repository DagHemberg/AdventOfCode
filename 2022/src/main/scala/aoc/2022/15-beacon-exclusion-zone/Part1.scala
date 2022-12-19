package aoc.y2022.day15
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(15, 2022)(1)(0):
  def name = "Beacon Exclusion Zone - Part 1"
  def solve(data: List[String]) = 
    val sensors = parse(data).toSet
    val beacons = sensors.map(_.beacon)

    sensors
      .flatMap(_.spots(2000000))
      .filterNot(beacons)
      .size

