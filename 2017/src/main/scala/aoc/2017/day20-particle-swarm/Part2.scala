package aoc.y2017.day20
import aoc.utils.*

object Part2 extends Problem("2017", "20", "2")(2):
  def name = "Particle Swarm - Part 2"
  def solve(data: Seq[String]) = 
    val particles = parse(data)

    particles
      .iterate(ps =>
        val ticked = ps.map(_.tick)
        val collided = ticked.groupBy(_.pos).filter(_._2.size > 1).keySet
        ticked.filterNot(p => collided.contains(p.pos))
      )(100).size
