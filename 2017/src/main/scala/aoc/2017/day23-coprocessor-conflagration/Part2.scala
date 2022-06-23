package aoc.y2017.day23
import aoc.utils.*

object Part2 extends Problem("2017", "23", "2")(917):
  def name = "Coprocessor Conflagration - Part 2"
  def solve(data: Seq[String]) = 
    def isPrime(n: Int) = (2 to math.sqrt(n).toInt).forall(n % _ != 0)
    val b = 100 * (data.head.words.last.toInt + 1000)
    (b to b+17000 by 17).filterNot(isPrime).size
