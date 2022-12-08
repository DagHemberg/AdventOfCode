package aoc.y2022.day07
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(7, 2022)(2)(24933642L):
  def name = "No Space Left On Device - Part 2"
  def solve(data: List[String]) = 
    val shell: Shell = parse(data)
    val root: Data = shell.data.refresh

    allFolders(shell, root)
      .map(_.size)
      .filter(root.size - _ <= 70000000L - 30000000L)
      .min
