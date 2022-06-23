package aoc.y2017.day23
import aoc.utils.*

object Part1 extends Problem("2017", "23", "1")(3969):
  def name = "Coprocessor Conflagration - Part 1"
  def solve(data: Seq[String]) = 
    val regs = ('a' to 'h').map(_.toString -> 0L).toMap

    def get(reg: String)(using regs: Map[String, Long]) = if regs.contains(reg) then regs(reg) else reg.toLong

    case class Program(pc: Long, regs: Map[String, Long], mulCounter: Int):
      def tick = copy(pc = pc + 1)

    Program(0, regs, 0).doUntil
      (p => p.pc < 0 || p.pc >= data.size)
      (p => 
        given Map[String, Long] = p.regs
        data(p.pc.toInt) match
          case s"set $reg $value" =>
            p.tick.copy(regs = p.regs.updated(reg, get(value)))
          case s"sub $reg $value" => 
            p.tick.copy(regs = p.regs + (reg -> (get(reg) - get(value))))
          case s"mul $reg $value" => 
            p.tick.copy(regs = p.regs + (reg -> (get(reg) * get(value))), mulCounter = p.mulCounter + 1)
          case s"jnz $reg $value" => 
            if get(reg) != 0 then p.copy(pc = p.pc + get(value)) else p.tick
      ).mulCounter