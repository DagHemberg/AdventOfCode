package aoc.day04
import aoc.utils.*

object Problem2 extends Solver("04", 1924):
  def name = "Giant Squid Bingo - Part 2"
  def solve(data: Vector[String]) = 
    given Vector[String] = data
    var boards = parsed
    val numbers = data.head.split(",").toVector.map(_.toInt)

    val winners = scala.collection.mutable.ListBuffer[(Board, Int)]()
    for i <- numbers do
      boards = boards.map(_.update(i))
      val b = boards.filter(_.hasWon)
      boards = boards.filter(!_.hasWon)
      if b.nonEmpty then winners ++= b.map(b => b -> i)

    val (last, n) = winners.last
    last.sumOfNonFlipped * n