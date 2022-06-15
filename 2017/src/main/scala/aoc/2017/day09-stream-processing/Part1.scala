package aoc.y2017.day09
import aoc.utils.*
import collection.mutable.{ArrayBuffer, Stack}

object Part1 extends Problem("2017", "09", "1")(51):
  def name = "Stream Processing - Part 1"
  def solve(data: Seq[String]) =
    val groups = data.map(_
      .replaceAll("!.", "")
      .replaceAll("<.*?>", "")
      .replaceAll(",", "")
    )
    
    def score(str: String) = 
      val (tot, _) = str.foldLeft((0, 1)) { case ((total, depth), c) =>
        c match
          case '{' => (total + depth, depth + 1)
          case '}' => (total, depth - 1)
      }
      tot

    groups.map(score).sum
