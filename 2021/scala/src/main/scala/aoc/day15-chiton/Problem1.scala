package aoc.day15
import aoc.utils.*
import scala.collection.mutable as mutable

object Problem1 extends Solver("15", 40):
  def name = "Chiton - Part 1"

  case class Node(pos: Index, cost: Int)
  def nodeOrder = new Ordering[Node]:
    def compare(a: Node, b: Node) = b.cost compare a.cost

  case class Edge(to: Index, cost: Int)
    // override def toString = s"$from ——$cost--> $to"

  extension (mat: Matrix[Int])
    def surrounding(i: Index) = 
      Vector(i.up, i.down, i.left, i.right).filterNot(mat.indexOutsideBounds)

  def solve(data: Vector[String]) = 
    // val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix

    // val start = Index(0, 0)
    // val end = Index(caves.height - 1, caves.width - 1)

    // val vtx = mutable.Set[Index](caves.indices.toVector.flatten*)
    // val q = mutable.PriorityQueue[Node]()(nodeOrder)
    // q.enqueue(caves.indices.map(i => i -> Int.MaxValue).toVector.flatten.map(Node.apply)*)
    // val dist = mutable.Map[Index, Int](start -> 0).withDefaultValue(Int.MaxValue)
    // val prev = mutable.Map[Index, Index]()
    // var sum = 0

    // while q.nonEmpty do
    //   val min = q.dequeue
    //   vtx -= min.pos
    //   if min.pos == end then
    //     val path = mutable.Stack[Index]()
    //     var current = end
    //     while current != start do
    //       path.push(current)
    //       current = prev(current)
    //     sum = path.toVector.map(caves.apply).sum
    //   else
    //     for pos <- caves surrounding min.pos if vtx(pos) do
    //       val alt = min.cost + caves.apply(pos)
    //       if alt < dist(min.pos) then
    //         q.enqueue(Node(pos, alt))
    //         dist(pos) = alt
    //         vtx += pos
    //         prev(pos) = min.pos

    // sum
    
    val caves = data.map(_.split("").toVector.map(_.toInt)).toMatrix

    // val pq = mutable.PriorityQueue[Node](caves
    //   .indices
    //   .toVector
    //   .flatten
    //   .updated(0, Node(Index(0,0), 0))*)(nodeOrder)

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
      val min = vertices.minBy(dist)
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