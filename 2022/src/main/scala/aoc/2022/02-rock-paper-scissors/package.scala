package aoc.y2022.day02
import problemutils.*, extensions.*

enum Move:
  def score = ordinal + 1
  def next = Move.values((ordinal + 1) % 3)
  def prev = Move.values((ordinal + 2) % 3)
  def winsOver(that: Move) = this == that.next
  case Rock, Paper, Scissors

def points(opponent: Move, me: Move) = 
  if opponent == me then 3
  else if me winsOver opponent then 6
  else 0

val decode = 
  import Move.*
  Map(
    "A" -> Rock,
    "B" -> Paper,
    "C" -> Scissors,
    "X" -> Rock,
    "Y" -> Paper,
    "Z" -> Scissors
  )

extension (ls: List[(Move, Move)]) 
  def totalScore = ls.sumBy((opp, me) => points(opp, me) + me.score)