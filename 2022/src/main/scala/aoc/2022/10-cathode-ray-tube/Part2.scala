package aoc.y2022.day10
import paut.aoc.*
import problemutils.*, extensions.*

import Console.*

object Part2 extends Problem(10, 2022)(2)(Skip):
  def name = "Cathode-Ray Tube - Part 2"
  def solve(data: List[String]) = 
    val registers = parse(data)  

    registers
      .last.output
      .toSeq
      .reshape(6, 40)
      .log

    "See above"
