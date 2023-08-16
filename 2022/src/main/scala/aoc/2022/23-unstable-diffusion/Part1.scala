package aoc.y2022.day23
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(23, 2022)(1)(110L):
  def name = "Unstable Diffusion - Part 1"
  def solve(data: List[String]) = 
    val grove = parse(data)
    val elves = grove.iterate(_.move)(10).elves

    val (minX, maxX) = elves.map(_.x).pipe(xs => (xs.min, xs.max))
    val (minY, maxY) = elves.map(_.y).pipe(ys => (ys.min, ys.max))
    (maxX - minX + 1) * (maxY - minY + 1) - elves.size
