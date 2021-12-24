package aoc.day22
import aoc.utils.*

object Problem2 extends Solver("22", 2759126877593581L):
  def name = "Reactor Reboot - Part 2"
  def solve(data: Vector[String]) = 
    given Vector[String] = data
    cuboids.map(_.size).sum