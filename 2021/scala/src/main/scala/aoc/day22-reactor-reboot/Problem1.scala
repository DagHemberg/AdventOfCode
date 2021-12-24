package aoc.day22
import aoc.utils.*

object Problem1 extends Solver("22", 590784L):
  def name = "Reactor Reboot - Part 1"
  def solve(data: Vector[String]) = 
    given Vector[String] = data.take(20)
    cuboids.map(_.size).sum
