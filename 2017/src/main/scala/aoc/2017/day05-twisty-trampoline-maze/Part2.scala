package aoc.y2017.day05
import aoc.utils.*

object Part2 extends Problem("2017", "05", "2")(10):
  def name = "Twisty Trampoline Maze - Part 2"
  def solve(data: Seq[String]) = 
    val maze = data.map(_.toInt)
    val (n, i, _) = loop(maze)
      ((n, i, maze) => (n + 1, i + maze(i), maze.updated(i, maze(i) + (if maze(i) >= 3 then -1 else 1))))

    n
