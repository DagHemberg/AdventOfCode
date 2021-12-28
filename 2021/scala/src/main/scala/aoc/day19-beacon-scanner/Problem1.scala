package aoc.day19
import aoc.utils.*

object Problem1 extends Solver("19", 79):
  def name = "Beacon Scanner - Part 1"
  def solve(data: Vector[String]) =
    val scanners = align(data)
    scanners.map(_.beacons.toSet).reduce(_ union _).size