package aoc.day04
import aoc.utils.*

object Problem1 extends Solver("04", 4512):
  def name = "Giant Squid Bingo - Part 1"
  def solve(data: Vector[String]) = 
    given Vector[String] = data
    var boards = parsed
    var numbers = data.head.split(",").toVector.map(_.toInt)

    var i = 0    
    while !boards.exists(_.hasWon) do
      i = numbers.head
      numbers = numbers.tail
      boards = boards.map(_.update(i))

    boards.find(_.hasWon).get.sumOfNonFlipped * i