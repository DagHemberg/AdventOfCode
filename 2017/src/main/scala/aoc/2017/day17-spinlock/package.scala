package aoc.y2017
import aoc.utils.*

package object day17:
  def jump(n: Int, pos: Int)(using steps: Int) = (pos + steps) % n
