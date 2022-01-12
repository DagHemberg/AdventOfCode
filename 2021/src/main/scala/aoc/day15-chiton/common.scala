package aoc.day15
import aoc.utils.*
import scala.collection.mutable as mutable

extension (mat: Matrix[Int])
  def surrounding(i: Index) =
    Set(i.up, i.down, i.left, i.right).filterNot(mat.indexOutsideBounds)

def graph(caves: Matrix[Int]) = caves
  .indices
  .toVector
  .flatten
  .flatMap(from => (caves surrounding from).map(to => Edge(from, to, caves(to))))
  .toSet