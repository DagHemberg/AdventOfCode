package aoc.y2017.day06
import aoc.utils.*
import collection.mutable as mutable

object Part2 extends Problem("2017", "06", "2")(4):
  def name = "Memory Reallocation - Part 2"
  def solve(data: Seq[String]) = 
    val banks = data.head.words.map(_.toInt).to(mutable.ArraySeq)
    val seen = count(banks) :+ banks
    val index = seen.find(b => seen.count(_ == b) > 1).get
    seen.size - seen.indexOf(index) - 1