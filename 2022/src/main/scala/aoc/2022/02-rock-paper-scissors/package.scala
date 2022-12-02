package aoc.y2022.day02
import problemutils.*, extensions.*

enum Move:
  def score = ordinal + 1
  def winsOver(that: Move) = this.ordinal == (that.ordinal + 1) % 3
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
  def totalScore = ls
    .map((opp, me) => points(opp, me) + me.score)
    .sum