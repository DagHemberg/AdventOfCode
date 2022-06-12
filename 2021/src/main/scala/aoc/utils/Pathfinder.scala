package aoc.utils

type Cost = Double
object Cost:
  def MaxValue: Cost = Double.MaxValue

case class Edge[N](from: N, to: N, cost: Cost):
  override  def toString = s"$from -> $to @ $cost"

case class Path[N](nodes: Seq[N], cost: Cost):
  override def toString = s"[${nodes.mkString(" -> ")}] @ $cost"

case class Pathfinder[N](graph: Set[Edge[N]], start: N, end: N, heuristic: N => Cost = (n: N) => 0):
  import scala.collection.mutable as mutable
  private case class CostNode(node: N, cost: Cost)
  private def nodeOrder = new Ordering[CostNode]:
    def compare(a: CostNode, b: CostNode) = b.cost compare a.cost

  def shortestPath =
    val neighbours = graph.groupBy(_.from) withDefaultValue Set.empty
    val queue = mutable.PriorityQueue((start, 0d))(Ordering.by((a, b) => -b))
    val previous = mutable.Map.empty[N, N]
    val distance = mutable.Map.empty[N, Double] withDefaultValue Double.MaxValue    
    distance(start) = 0

    var found = false
    while !found && queue.nonEmpty do
      val (min, _) = queue.dequeue

      if min == end then found = true
      else for edge <- neighbours(min) do
        val alt = distance(min) + edge.cost
        val destination = edge.to
        if alt < distance(destination) then
          queue.enqueue((destination, alt + heuristic(destination)))
          distance(destination) = alt
          previous(destination) = min

    def backtrack(current: N): Vector[N] =
      if previous(current) == start then Vector(start, current)
      else backtrack(previous(current)) :+ current

    if found then Some(Path(backtrack(end), distance(end))) 
    else None