package aoc.day21
import aoc.utils.*

case class Player(score: Int, position: Int, going: Boolean):
  def updated(addedScore: Int) = 
    if going then 
      val newPos = (position + addedScore - 1) % 10 + 1
      Player(score + newPos, newPos, false)
    else 
      Player(score, position, true)