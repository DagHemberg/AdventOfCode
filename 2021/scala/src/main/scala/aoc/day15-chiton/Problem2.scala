package aoc.day15
import aoc.utils.*
import scala.collection.mutable as mutable

object Problem2 extends Solver("15", 315):
  def name = "Chiton - Part 2"

  def solve(data: Vector[String]) = 
    val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix
    val manyCaves = Matrix(5,5)(_ + _).map(sector => caves.map(x => (x + sector - 1) % 9 + 1)).flatten

    given Matrix[Int] = manyCaves
    path.map(manyCaves.apply).sum