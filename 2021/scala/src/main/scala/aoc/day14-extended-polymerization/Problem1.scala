package aoc.day14
import aoc.utils.*

object Problem1 extends Solver("14", 1588L):
  def name = "Extended Polymerization - Part 1"
  def solve(data: Vector[String]) = 
    val template = data.head
    val pairInsertions = data
      .drop(2)
      .map(s => s.split(" -> ").head -> s"${s.head}${s.split(" -> ").last}${s.tail.head}")
      .toMap

    val extended = (1 to 10).foldLeft(template)((polymer, i) => (polymer
      .sliding(2)
      .toVector
      .map(pair => pairInsertions.getOrElse(pair, pair))
      .map(_.dropRight(1)) :+ template.last)
      .mkString
    )

    val sizes = extended.groupBy(identity).mapValues(_.size)
    sizes.maxBy(_._2)._2 - sizes.minBy(_._2)._2