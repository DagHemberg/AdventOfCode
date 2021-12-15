package aoc.day14
import aoc.utils.*

object Problem2 extends Solver("14", 2188189693529L):
  def name = "Extended Polymerization - Part 2"
  def solve(data: Vector[String]) =
    val template = data.head
    val newPairs = data
      .drop(2)
      .map { case s"$a -> $b" => a -> Vector(s"${a.head}$b", s"$b${a.last}") }
      .toMap

    val pairInsertions = data
      .drop(2)
      .map { case s"$a -> $b" => a -> b }
      .toMap

    var values = template.toVector
      .map(_.toString)
      .groupBy(identity)
      .view
      .mapValues(_.size.toLong)
      .toMap

    var pairs = template
      .sliding(2)
      .toVector
      .groupBy(identity)
      .view
      .mapValues(_.size.toLong)
      .toMap

    for i <- 1 to 40 do
      values = pairs.toVector
        .map((pair, value) => pairInsertions(pair) -> value)
        .foldLeft(Vector.empty[(String, Long)])((acc, curr) =>
          if acc.exists(_._1 == curr._1) then
            val el = acc.find(_._1 == curr._1).get
            acc.updated(acc.indexOf(el), (el._1, el._2 + curr._2))
          else acc :+ curr
        )
        .foldLeft(values.toVector)((acc, curr) =>
          if acc.exists(_._1 == curr._1) then
            val el = acc.find(_._1 == curr._1).get
            acc.updated(acc.indexOf(el), (el._1, el._2 + curr._2))
          else acc :+ curr
        )
        .toMap

      pairs = pairs.toVector
        .flatMap((pair, amount) =>
          newPairs(pair).map(newPair => newPair -> amount)
        )
        .foldLeft(Vector.empty[(String, Long)])((acc, curr) =>
          if acc.exists(_._1 == curr._1) then
            val el = acc.find(_._1 == curr._1).get
            acc.updated(acc.indexOf(el), (el._1, el._2 + curr._2))
          else acc :+ curr
        )
        .toMap

    
    values.maxBy(_._2)._2 - values.minBy(_._2)._2
