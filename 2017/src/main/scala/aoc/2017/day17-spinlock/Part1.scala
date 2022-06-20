package aoc.y2017.day17
import aoc.utils.*

object Part1 extends Problem("2017", "17", "1")(638):
  def name = "Spinlock - Part 1"
  def solve(data: Seq[String]) =
    given steps: Int = data.head.toInt
    
    extension (ls: List[Int]) def insertAfter(i: Int, x: Int) =
      ls.take(i + 1) ++ List(x) ++ ls.drop(i + 1)

    val spun = (List(0), 1, 1)
      .iterate((ls, n, pos) =>
        (
          ls.insertAfter(jump(n, pos), n),
          n + 1,
          jump(n, pos) + 1
        )
      )(2017)
      .head

    spun(spun.indexOf(2017) + 1)
