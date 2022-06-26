package aoc.y2017.day25
import aoc.utils.*

object Part1 extends Problem("2017", "25", "1")(2):
  def name = "The Halting Problem - Part 1"
  def solve(data: Seq[String]) = 
    val (startingState, conditions, steps) = parse(data)
    val turingMachine = TuringMachine(Set.empty, 0, startingState)

    turingMachine.iterate(m => 
      conditions(m.state)(m.tape(m.pos).toInt) match
        case Instruction(write, dir, next) =>
          val tape = write match
            case 0 => m.tape - m.pos
            case 1 => m.tape + m.pos
          TuringMachine(tape, m.pos + dir, next)
      )(steps)
      .tape.size
