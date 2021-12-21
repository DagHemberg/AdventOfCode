package aoc.day04
import aoc.utils.*

object Problem1 extends Solver("04", 1):
  def name = "Giant Squid Bingo - Part 1"
  def solve(data: Vector[String]) = 
    val boards = data
      .drop(2)
      .filter(!_.isEmpty)
      .sliding(5)
      .toVector
      .map(_.map(_.split(raw"\s+").toVector.filter(_.nonEmpty)))
      .map(_.toMatrix.map(_.toInt))
      .map(b => Board(b.map(t => Tile(t)))).debug

    val ll = LazyList.iterate(boards)(board => board)
    val nums = data.head.split(",").toVector.map(_.toInt)
    ll(nums.zipWithIndex.dropWhile((num, i) => ll(i).exists(_.hasWon)).head._2)

    5