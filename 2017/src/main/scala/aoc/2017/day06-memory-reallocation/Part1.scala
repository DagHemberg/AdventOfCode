package aoc.y2017.day06
import aoc.utils.*
import collection.mutable as mutable

object Part1 extends Problem("2017", "06", "1")(5):
  def name = "Memory Reallocation - Part 1"
  def solve(data: Seq[String]) = 
    val banks = data.head.words.map(_.toInt).to(mutable.ArraySeq)
    count(banks).size