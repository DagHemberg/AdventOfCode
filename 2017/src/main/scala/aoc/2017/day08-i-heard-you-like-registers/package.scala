package aoc.y2017
import aoc.utils.*

package object day08:
  case class Command(mod: String, by: Int)
  case class Predicate(reg: String, op: String, value: Int)
  case class Instruction(reg: String, cmd: Command, pred: Predicate)

  def parse(data: Seq[String]) = data.map {
    case s"$reg1 $mod $by if $reg2 $op $value" => 
      Instruction(reg1, Command(mod, by.toInt), Predicate(reg2, op, value.toInt))
  }

  def validate(cond: Predicate)(using register: Map[String, Int]): Boolean =
    val reg = cond.reg
    cond.op match
      case ">" => register(reg) > cond.value
      case "<" => register(reg) < cond.value
      case ">=" => register(reg) >= cond.value
      case "<=" => register(reg) <= cond.value
      case "==" => register(reg) == cond.value
      case "!=" => register(reg) != cond.value

  def newValue(reg: String, cmd: Command)(using register: Map[String, Int]): Int =
    cmd.mod match
      case "inc" => register(reg) + cmd.by
      case "dec" => register(reg) - cmd.by