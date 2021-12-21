package aoc.day04
import aoc.utils.*

case class Tile(number: Int, flipped: Boolean = false)
  // override def toString = if (flipped) "X" else number.toString
class Board(data: Matrix[Tile], won: Boolean = false):
  // override def toString = s"\n${data.input.map(_.mkString("\t")).mkString("\n")}\n"
  def hasWon = Vector(data.row, data.col).exists(f => (0 to 4).map(f).exists(_.forall(_.flipped))) // cursed
  def update(i: Int) = 
    val newData = data.map(x => if x.number == i then x.copy(flipped = true) else x)
    Board(newData, Board(newData, won).hasWon) // ???
  def sumOfNonFlipped = data.toVector.flatten.filter(!_.flipped).map(_.number).sum