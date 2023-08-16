package aoc.y2022.day02
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(2, 2022)(1)(15):
  def name = "Rock Paper Scissors - Part 1"
  def solve(data: List[String]) = 
    data
      .map:
        case s"$opp $me" => decode(opp) -> decode(me)
      .totalScore
