package aoc.y2022.day10
import problemutils.*, extensions.*

case class Register(value: Int = 1, cycle: Int = 0, output: String = "")

def out(crtPos: Int, spritePos: Int) = 
  if Seq(spritePos, spritePos + 1, spritePos + 2) contains (crtPos + 1) % 40
  then "█" else " "

def parse(data: List[String]) = 
  data.scanLeft(new Register) { (reg, line) => 
    val Register(value, cycle, output) = reg
    line match
      case s"addx $n" => 
        val fst = out(cycle, value)
        val snd = out(cycle + 1, value)
        Register(value + n.toInt, cycle + 2, s"$output$fst$snd")
      case "noop"     => 
        Register(value, cycle + 1, s"$output${out(cycle, value)}")
  }
