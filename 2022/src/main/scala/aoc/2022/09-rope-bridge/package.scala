package aoc.y2022.day09
import problemutils.*, extensions.*

import Cardinal.*

def determineStepDirection(a: Pos2D, b: Pos2D) = a - b match
  case ( 2,  2) => NorthWest
  case ( 2, -2) => NorthEast
  case (-2,  2) => SouthWest
  case (-2, -2) => SouthEast
  case ( 2,  _) => North
  case (-2,  _) => South
  case ( _,  2) => West
  case ( _, -2) => East

case class Rope(knots: List[Pos2D], visited: Set[Pos2D]):      
  def moved(steps: Cardinal*) = steps
    .foldLeft(this) { (rope, step) => 
      val newKnots = rope
        .knots.tail
        .scanLeft(rope.knots.head + step.toPos2D) { (head, tail) => 
          if (head.neighbours :+ head) contains tail then tail
          else head + determineStepDirection(head, tail).toPos2D
        }
      Rope(newKnots, rope.visited + newKnots.last)
    }

object Rope:
  def ofLength(len: Int) = Rope(List.fill(len)(0, 0), Set((0, 0)))

def parse(data: List[String])(using init: Rope) = data.foldLeft(init) { (rope, line) => 
  val steps = line match
    case s"$dir $amount" => List.fill(amount.toInt) { dir match
      case "U" => North
      case "D" => South
      case "R" => East
      case "L" => West
    }
  rope.moved(steps*)
}.visited
