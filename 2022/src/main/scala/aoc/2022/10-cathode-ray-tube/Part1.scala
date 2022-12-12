package aoc.y2022.day10
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(10, 2022)(1)(13140):
  def name = "Cathode-Ray Tube - Part 1"
  def solve(data: List[String]) = 
    val registers = parse(data)

    List
      .iterate(20, 6)(_ + 40)
      .sumBy { cycle => 
        cycle * registers(registers.indexWhere(_.cycle >= cycle) - 1).value
      }
    