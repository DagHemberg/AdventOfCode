package aoc.day15
import aoc.utils.*
import scala.collection.mutable as mutable

object Problem2 extends Solver("15", 315):
  def name = "Chiton - Part 2"

  case class Node(pos: Index, cost: Int)
  def nodeOrder = new Ordering[Node]:
    def compare(a: Node, b: Node) = b.cost compare a.cost

  case class Edge(to: Index, cost: Int)

  extension (mat: Matrix[Int])
    def surrounding(i: Index) = 
      Vector(i.up, i.down, i.left, i.right).filterNot(mat.indexOutsideBounds)

  def solve(data: Vector[String]) = 
    val firstCaves = data.map(_.split("").toVector.map(_.toInt)).toMatrix
    val caves = Matrix(5,5)(_ + _).map(sector => firstCaves.map(x => (x + sector - 1) % 9 + 1)).flatten

    val vertices = mutable.Set(caves
      .indices
      .toVector
      .flatten*)

    val edges = mutable.Map(caves
      .indices
      .toVector
      .flatten
      .map(i => (i, caves.surrounding(i).map(s => Edge(s, caves(i)))))*
    )

    val start = Index(0, 0)
    val end = Index(caves.height - 1, caves.width - 1)

    val dist = mutable.Map[Index, Int]().withDefaultValue(Int.MaxValue)
    val prev = mutable.Map[Index, Index]()
    dist(start) = 0

    var found = false
    while !found && vertices.nonEmpty do
      val min = vertices.minBy(dist) // this is the bitch
      vertices -= min
      
      if min == end then found = true
      else for edge <- edges(min) do
        val alt = dist(min) + edge.cost
        if alt < dist(edge.to) then
          dist(edge.to) = alt
          prev(edge.to) = min

    val path = mutable.Stack[Index]()
    var current = end
    while current != start do
      path.push(current)
      current = prev(current)

    path.toVector.map(caves.apply).sum