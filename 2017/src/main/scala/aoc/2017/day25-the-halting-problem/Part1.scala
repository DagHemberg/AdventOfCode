package aoc.y2017.day25
import aoc.utils.*

object Part1 extends Problem("2017", "25", "1")(2):
  def name = "The Halting Problem - Part 1"
  def solve(data: Seq[String]) = 
    val (startingState, conditions, steps) = parse(data)
    val tape = TuringMachine(Set.empty, 0, startingState)

    tape.iterate(t => 
      conditions(t.state)(t.tape(t.pos).toInt) match
        case Instruction(write, dir, next) =>
          val tape = write match
            case 0 => t.tape - t.pos
            case 1 => t.tape + t.pos
          TuringMachine(tape, t.pos + dir, next)
      )(steps)
      .tape.size
