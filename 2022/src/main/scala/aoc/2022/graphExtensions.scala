package problemutils.classes.graphs

import problemutils.*, extensions.*

// graphs:
// - .empty should give generic graph

extension [V] (g: Graph.type)
  def fromMap(map: Map[V, IterableOnce[V]]) = 
    val graph: Graph[V] = Graph.empty
    map.foldLeft(graph):
      case (graph, (from, connections)) => 
        graph union connections.iterator.toSet.map(from ->> _).foldLeft(Graph.empty: Graph[V])(_ + _)

  def fromMatrix(matrix: Matrix[V], joined: (V, V) => Boolean, cost: (V, V) => Double = (a: V, b: V) => 1.0) = 
    val vec = matrix.toVector
    val edges = 
      for v1 <- vec; v2 <- vec if joined(v1, v2)
      yield Edge(v1, v2, cost(v1, v2))
    
    Graph.from(edges)

extension [V] (p: Pathfinder.type)
  def backtrack2(current: V)(using prev: Map[V, V], start: V): Vector[V] =
    if prev(current) == start then Vector(start, current)
    else backtrack2(prev(current)) :+ current

  def fastBfs2
    (start: V, stoppingPredicate: V => Boolean, heuristic: V => Double)
    (using adj: V => Set[Edge[V]]) = 

    import collection.mutable

    val pq = mutable.PriorityQueue(start -> 0d)(Ordering.by(_._2)).reverse
    val prev = mutable.Map.empty[V, V]
    val dist = mutable.Map(start -> 0d) withDefaultValue Double.PositiveInfinity

    var found = false
    while !found && pq.nonEmpty do
      val (min, _) = pq.dequeue
      // val (min, _) = pq.logAttrIf(_.size % 5 == 0)(q => s"q: ${q.size}").dequeue
      // val (min, _) = pq.logAttr(q => s"q: ${q.head}").dequeue

      if stoppingPredicate(min) then found = true
      else for edge <- adj(min) do
        val alt = dist(min) + edge.weight
        val dest = edge.to
        if alt < dist(dest) then
          dist(dest) = alt
          prev(dest) = min
          pq.enqueue((dest, alt + heuristic(dest)))

    (dist.toMap, prev.toMap, found)

extension [V] (graph: Graph[V])
  def pathFromUntil
    (start: V, endPred: V => Boolean, heuristic: V => Double = (a: V) => 0.0)
    (using V => Set[Edge[V]]): Option[Path[V]] = 

    val (dist, prev, found) = Pathfinder.fastBfs2(start, endPred, heuristic)
    val end = prev.find((a, b) => endPred(a)).get._1

    if found then Some(Path(Pathfinder.backtrack2(end)(using prev, start), dist(end)))
    else None