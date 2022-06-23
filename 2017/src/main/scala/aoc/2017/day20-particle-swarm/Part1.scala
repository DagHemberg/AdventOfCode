package aoc.y2017.day20
import aoc.utils.*

object Part1 extends Problem("2017", "20", "1")(0):
  def name = "Particle Swarm - Part 1"
  def solve(data: Seq[String]) = 
    val particles = parse(data)
    particles.minBy(_.acc manhattan origin).id
