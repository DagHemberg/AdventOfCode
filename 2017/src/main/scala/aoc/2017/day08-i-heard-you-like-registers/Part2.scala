package aoc.y2017.day08
import aoc.utils.*

object Part2 extends Problem("2017", "08", "2")(10):
  def name = "I Heard You Like Registers - Part 2"
  def solve(data: Seq[String]) = 
    val instructions = parse(data)
    val (maxFound, registers) = instructions.foldLeft((0, Map.empty[String, Int] withDefaultValue 0)){ 
      case ((n, regs), inst) => 
        given Map[String, Int] = regs
        val newRegs = 
          if validate(inst.pred) then regs + (inst.reg -> newValue(inst.reg, inst.cmd))
          else regs
        (n max newRegs.values.maxOption.getOrElse(0), newRegs)
    }

    maxFound
