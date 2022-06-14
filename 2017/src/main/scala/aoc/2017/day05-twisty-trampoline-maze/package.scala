package aoc.y2017
import aoc.utils.*

package object day05:
  def loop(maze: Seq[Int]) = (0, 0, maze).doUntil((_, i, maze) => !maze.isDefinedAt(i))
