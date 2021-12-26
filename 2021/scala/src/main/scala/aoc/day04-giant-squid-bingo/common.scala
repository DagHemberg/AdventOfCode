package aoc.day04
import aoc.utils.*

case class Tile(number: Int, flipped: Boolean = false)

class Board(data: Matrix[Tile], won: Boolean = false):
  def hasWon = Seq(data.row, data.col).exists(f => (0 to 4).map(f).exists(_.forall(_.flipped))) // cursed

  def update(i: Int) = 
    val newData = data.map(x => if x.number == i then x.copy(flipped = true) else x)
    Board(newData, Board(newData, won).hasWon) // ???

  def sumOfNonFlipped = data.toVector.flatten.filter(!_.flipped).map(_.number).sum

def parsed(using data: Vector[String]) = data
  .drop(2)
  .filter(!_.isEmpty)
  .sliding(5, 5)
  .toVector
  .map(_.map(_.split(raw"\s+").toVector.filter(_.nonEmpty)))
  .map(_.toMatrix.map(_.toInt))
  .map(b => Board(b.map(t => Tile(t))))