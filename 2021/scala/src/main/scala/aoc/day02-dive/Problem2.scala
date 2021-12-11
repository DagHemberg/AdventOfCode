package aoc.day02
import aoc.utils.*

object Problem2 extends Solver("02", 900):
  def name = "Dive! - Part 2"  
  def solve(data: Vector[String]) =
    var aim   = 0
    var hPos  = 0
    var depth = 0

    def updatePos(command: String, value: Int) = command match {
      case "down" => aim += value
      case "up"   => aim -= value
      case "forward" => 
        hPos += value
        depth += aim * value 
    }

    data.foreach {
      case s"$command $value" => updatePos(command, value.toInt)
    }

    hPos * depth