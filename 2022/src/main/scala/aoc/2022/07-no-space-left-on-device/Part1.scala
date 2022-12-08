package aoc.y2022.day07
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(7, 2022)(1)(95437L):
  def name = "No Space Left On Device - Part 1"
  def solve(data: List[String]) = 
    val shell: Shell = parse(data)
    val root: Data = shell.data.refresh

    allFolders(shell, root)
      .filter(_.size < 100000)
      .sumBy(_.size)