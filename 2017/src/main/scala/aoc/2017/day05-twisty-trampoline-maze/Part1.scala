package aoc.y2017.day05
import aoc.utils.*

object Part1 extends Problem("2017", "05", "1")(5):
  def name = "Twisty Trampoline Maze - Part 1"
  def solve(data: Seq[String]) = 
    val maze = data.map(_.toInt)
    val (n, i, _) = loop(maze)
      ((n, i, maze) => (n + 1, i + maze(i), maze.updated(i, maze(i) + 1)))

    n
