package aoc.y2017.day08
import aoc.utils.*

object Part1 extends Problem("2017", "08", "1")(1):
  def name = "I Heard You Like Registers - Part 1"
  def solve(data: Seq[String]) = 
    val instructions = parse(data)
    val registers = instructions.foldLeft(Map.empty[String, Int] withDefaultValue 0)((regs, inst) => 
      given Map[String, Int] = regs
      if validate(inst.pred) then regs + (inst.reg -> newValue(inst.reg, inst.cmd))
      else regs
    )

    registers.values.max
