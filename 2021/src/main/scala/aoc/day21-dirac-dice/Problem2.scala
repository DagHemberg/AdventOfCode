package aoc.day21
import aoc.utils.*

object Problem2 extends Solver("21", 444356092776315L):
  def name = "Dirac Dice - Part 2"
  def solve(data: Vector[String]) = 
    val players = 
      data.map { case s"Player $n starting position: $position" => Player(0, position.toInt, n == "1") }

    val possibleThrows = (1 to 3)
      .flatMap(x => 1 to 3)
      .permutations.toSet
      .map(_.take(3))
      .groupBy(_.sum)
      .map(_ -> _.size)

    def play(players: Vector[Player]): (Long, Long) = 
      if      players.head.score >= 21 then (1, 0) 
      else if players.last.score >= 21 then (0, 1)
      else 
        var p1Total, p2Total = 0L
        for (throwSum, nbrUniverses) <- possibleThrows do
          val (p1Score, p2Score) = play(players.map(_.updated(throwSum)))
          p1Total += p1Score * nbrUniverses
          p2Total += p2Score * nbrUniverses

        (p1Total, p2Total)

    val (p1Wins, p2Wins) = play(players)
    p1Wins max p2Wins