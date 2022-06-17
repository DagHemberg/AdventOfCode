package aoc.y2017.day13
import aoc.utils.*

object Part1 extends Problem("2017", "13", "1")(24):
  def name = "Packet Scanners - Part 1"
  def solve(data: Seq[String]) = 
    val scanners = parse(data)
    def launched(rocket: Rocket) = 
      rocket.doUntil(_.passedThrough)(_.fly)
    
    launched(Rocket(scanners)).severity
