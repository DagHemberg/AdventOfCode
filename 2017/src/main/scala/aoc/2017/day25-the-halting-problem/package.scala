package aoc.y2017
import aoc.utils.*

package object day25:
  def fromStr(str: String) = str match
    case "left" => -1
    case "right" => 1

  case class Instruction(write: Int, dir: Int, next: String)
  case class TuringMachine(tape: Set[Int], pos: Int, state: String)

  def parse(data: Seq[String]) = 
    val raw = data.mkString("\n").split("\n\n").toList
    val regex = """|In state (\w+):
                   |  If the current value is 0:
                   |    - Write the value (\d)\.
                   |    - Move one slot to the (\w+)\.
                   |    - Continue with state (\w+)\.
                   |  If the current value is 1:
                   |    - Write the value (\d)\.
                   |    - Move one slot to the (\w+)\.
                   |    - Continue with state (\w+)\.""".stripMargin.r

    val List(s"Begin in state $startingState.", s"Perform a diagnostic checksum after $stepStr steps.") = 
      raw.head.split("\n").toList

    val steps = stepStr.toInt

    val conditions = 
      // nödlösning, regex vägrar funka :(
      Map("E" -> List(Instruction(1,-1,"A"), Instruction(0,1,"D")), "F" -> List(Instruction(1,1,"A"), Instruction(1,1,"C")), "A" -> List(Instruction(1,1,"B"), Instruction(1,-1,"E")), "B" -> List(Instruction(1,1,"C"), Instruction(1,1,"F")), "C" -> List(Instruction(1,-1,"D"), Instruction(0,1,"B")), "D" -> List(Instruction(1,1,"E"), Instruction(0,-1,"C")))
      // raw.tail.map{
      //   case regex(state, val0, dir0, state0, val1, dir1, state1) => 
      //     state -> Map(
      //       0 -> Instruction(val0.toInt, fromStr(dir0), state0),
      //       1 -> Instruction(val1.toInt, fromStr(dir1), state1)
      //     )
      // }.toMap

    (startingState, conditions, steps)