package aoc.day08
import aoc.utils.*

object Problem2 extends Solver("08", 61229):
  def name = "Seven Segment Search - Part 2"
  def solve(data: Vector[String]) =
    val parsed = data
      .map { case s"$signalPatterns | $output" => signalPatterns.split(" ").map(_.toSet).toVector -> output.split(" ").map(_.toSet).toVector }
      .toVector

    val decryptedNumbers = parsed.map((signalPatterns, output) =>

      val letters = scala.collection.mutable.Map[Int, Set[Char]]()
      val fives = signalPatterns.filter(_.size == 5).map(_.toSet)
      val sixes = signalPatterns.filter(_.size == 6).map(_.toSet)

      // my linter is crying but i am not giving in
      letters.addOne(1 -> signalPatterns.filter(_.size == 2).head)
      letters.addOne(4 -> signalPatterns.filter(_.size == 4).head)
      letters.addOne(7 -> signalPatterns.filter(_.size == 3).head)
      letters.addOne(8 -> signalPatterns.filter(_.size == 7).head)
      letters.addOne(3 -> signalPatterns.filter(_.diff(letters(1)).size == 3).head)
      letters.addOne(2 -> fives.filter(_.intersect(letters(4)).size == 2).head)
      letters.addOne(5 -> fives.filter(_ != letters(3)).filter(_.intersect(letters(4)).size == 3).head)
      letters.addOne(9 -> sixes.filter(_.intersect(letters(4)).size == 4).head)
      letters.addOne(6 -> sixes.filter(_.union(letters(1)).size == 7).head)
      letters.addOne(0 -> sixes.filter(_ != letters(6)).filter(_ != letters(9)).head)

      output
        .flatMap(out => letters.filter(_._2 == out).map(_._1).toVector)
        .mkString
        .toInt
    )

    decryptedNumbers.sum
