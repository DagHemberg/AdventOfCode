package aoc.knattra.problem4
import aoc.utils.{*, given}

object Problem4 extends Solver("knattra4", 15):
  def name = "Tourist in the Spirit Realm"
  def solve(data: Vector[String]) =
    val raw = data.mkString("\n")
    import org.json4s.*
    import org.json4s.native.JsonMethods.*
    implicit val formats: Formats = DefaultFormats

    val jsonGraph = parse(raw)
    val start = (jsonGraph \ "start_node").extract[Int]
    val stop = (jsonGraph \ "finish_node").extract[Int]

    val graph = (jsonGraph \ "nodes")
      .extract[List[JObject]]
      .flatMap(obj =>
        val from  = (obj \ "id").extract[Int]
        val tos   = (obj \ "edges" \ "destination_node").extract[List[Int]]
        val costs = (obj \ "edges" \ "time_spent").extract[List[Int]]
        (tos zip costs).map((to, cost) => Edge(from, to, cost))
      ).toSet

    val path = Pathfinder(graph, start, stop).shortestPath.get
    path.nodes.sliding(2).toVector.map { case Vector(from, to) => s"($from:$to)" }.mkString(",").log("green")
    path.cost.toInt