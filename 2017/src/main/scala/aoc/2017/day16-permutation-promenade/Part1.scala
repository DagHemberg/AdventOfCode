package aoc.y2017.day16
import aoc.utils.*

object Part1 extends Problem("2017", "16", "1")("paedcbfghijklmno"):
  def name = "Permutation Promenade - Part 1"
  def solve(data: Seq[String]) = 
    val instructions = parse(data)
    programs.dance(using instructions)