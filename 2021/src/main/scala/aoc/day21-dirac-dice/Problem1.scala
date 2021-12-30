package aoc.day21
import aoc.utils.*

object Problem1 extends Solver("21", 739785L):
  def name = "Dirac Dice - Part 1"
  def solve(data: Vector[String]) = 
    var players = 
      data.map { case s"Player $n starting position: $position" => Player(0, position.toInt, n == "1") }

    var round = 1
    var timesRolled = 0

    def roll(round: Int) = Vector(-1, 0, 1).map(n => ((n - 1 + (round * 3)) - 1) % 100 + 1)
    var firstPlayersTurn = true
    while players.forall(_.score < 1000) do      
      players = players.map(_.updated(roll(round).sum))
      round += 1
      timesRolled += 3

    players.find(_.score < 1000).map(_.score).get * timesRolled
