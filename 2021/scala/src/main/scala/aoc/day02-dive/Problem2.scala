package aoc.day02

import aoc.utils.*

object Problem2 extends Solver("02", 900):
  def solve(data: Vector[String]) =
    val horizontals = data filter (_.startsWith("forward"))
    val verticals = data diff horizontals

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

    data.foreach(x => 
      val command = x.split(" ")(0)
      val value = x.split(" ")(1).toInt
      updatePos(command, value)
    )

    hPos * depth