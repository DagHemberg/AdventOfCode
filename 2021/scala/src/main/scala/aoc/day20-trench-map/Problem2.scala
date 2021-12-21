package aoc.day20
import aoc.utils.*

object Problem2 extends Solver("20", 3351L):
  def name = "Trench Map - Part 2"
  def solve(data: Vector[String]) = 
    given algorithm: String = data.head
    val startingImage: Image = Image(data.drop(2).map(_.split("").toVector).toMatrix, 0)

    LazyList.iterate(startingImage)(_.enhance)(50).countLit