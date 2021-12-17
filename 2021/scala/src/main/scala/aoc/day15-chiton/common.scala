package aoc.day15
import aoc.utils.*
import scala.collection.mutable as mutable

case class Edge(to: Index, cost: Int)
case class Node(pos: Index, cost: Int)
def nodeOrder = new Ordering[Node]:
  def compare(a: Node, b: Node) = b.cost compare a.cost

extension (mat: Matrix[Int])
  def surrounding(i: Index) =
    Set(i.up, i.down, i.left, i.right).filterNot(mat.indexOutsideBounds)

def path(using caves: Matrix[Int]) =
  val edges = mutable.Map(caves
    .indices
    .toVector
    .flatten
    .map(index =>
      index -> (caves surrounding index).map(s => Edge(s, caves(index))))*
  )

  val start = Index(0, 0)
  val end = Index(caves.height - 1, caves.width - 1)

  val dist = mutable.Map.empty[Index, Int] withDefaultValue Int.MaxValue
  val prev = mutable.Map.empty[Index, Index]
  val q = mutable.PriorityQueue(Node(start, 0))(nodeOrder)
  dist(start) = 0

  var found = false
  while !found && q.nonEmpty do
    val min = q.dequeue.pos

    if min == end then found = true
    else for edge <- edges(min) do
      val alt = dist(min) + edge.cost
      if alt < dist(edge.to) then
        q.enqueue(Node(edge.to, alt))
        dist(edge.to) = alt
        prev(edge.to) = min

  def backtrack(curr: Index): Vector[Index] =
    if prev(curr) == start then Vector(curr)
    else backtrack(prev(curr)) :+ curr

  backtrack(end)
