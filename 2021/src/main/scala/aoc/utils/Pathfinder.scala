package aoc.utils

case class Edge[N](from: N, to: N, cost: Double):
  override  def toString = s"$from -> $to @ $cost"
case class Path[N](nodes: Seq[N], cost: Double):
  override def toString = s"[${nodes.mkString(" -> ")}]: $cost"

case class Pathfinder[N](graph: Set[Edge[N]], start: N, end: N, heuristic: N => Double = (n: N) => 0):
  import scala.collection.mutable as mutable
  private case class Cost[A](node: A, cost: Double)
  private def nodeOrder = new Ordering[Cost[N]]:
    def compare(a: Cost[N], b: Cost[N]) = b.cost compare a.cost

  private def shortestPath =
    val edges = graph.groupBy(_.from)
    val q = mutable.PriorityQueue(Cost(start, 0))(nodeOrder)
    val prev = mutable.Map.empty[N, N]
    val dist = mutable.Map.empty[N, Double] withDefaultValue Double.MaxValue    
    dist(start) = 0    

    var found = false
    while !found && q.nonEmpty do
      val min = q.dequeue.node

      if min == end then found = true
      else for edge <- edges(min) do
        val alt = dist(min) + edge.cost
        if alt < dist(edge.to) then
          q.enqueue(Cost(edge.to, alt + heuristic(edge.to)))
          dist(edge.to) = alt
          prev(edge.to) = min

    def backtrack(curr: N): Vector[N] =
      if prev(curr) == start then Vector(start, curr)
      else backtrack(prev(curr)) :+ curr

    if found then Some(Path(backtrack(end), dist(end))) 
    else None

  lazy val path = shortestPath