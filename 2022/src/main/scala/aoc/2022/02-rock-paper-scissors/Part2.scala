package aoc.y2022.day02
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(2, 2022)(2)(12):
  def name = "Rock Paper Scissors - Part 2"
  def solve(data: List[String]) = 
    def decide(opp: Move, outcome: String) = 
      outcome match
        case "X" => opp.prev // player loses
        case "Z" => opp.next // player wins
        case "Y" => opp      // draw

    data
      .map { case s"$opp $strat" => decode(opp) -> decide(decode(opp), strat) }
      .totalScore
