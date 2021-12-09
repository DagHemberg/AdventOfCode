package aoc.day06
import aoc.utils.*

object Problem2 extends Solver("06", 26984457539L):
  def name = "Lanternfish - Part 2"
  def solve(data: Vector[String]) = 
    val input = data.map(_.toInt).toVector
    val fishes = (0 to 8).map(i => input.count(_ == i)).toVector.map(_.toLong)
    
    (0 until 256).foldLeft(fishes)((c, _) => c.tail.updated(6, c.tail(6) + c.head) :+ c.head).sum