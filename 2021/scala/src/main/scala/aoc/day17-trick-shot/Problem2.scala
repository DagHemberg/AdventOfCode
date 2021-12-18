package aoc.day17
import aoc.utils.*

object Problem2 extends Solver("17", 112):
  def name = "Trick Shot - Part 2"

  class Vel(x: Int, y: Int) extends Pos(x, y)
  case class Target(xs: Range, ys: Range)
  case class Probe(vel: Vel, pos: Pos)(using target: Target):
    override def toString = s"v=${vel.tuple} @ $pos"
    def stepOnce = Probe(Vel(if vel.x != 0 then vel.x - 1 else 0, vel.y - 1), pos + vel)
    def missedTarget = pos.y < target.ys.min
    def inTarget = (target.xs contains pos.x) && (target.ys contains pos.y)

  def solve(data: Vector[String]) = 
    val (xRange, yRange) = data
      .map{ case s"target area: x=$x1..$x2, y=$y1..$y2" => (x1.toInt to x2.toInt, y1.toInt to y2.toInt) }
      .head

    given Target = Target(xRange, yRange)

    extension (p: Probe)
      def launch: Option[Probe] = 
        if p.missedTarget then None
        else if p.inTarget then Some(p)
        else p.stepOnce.launch

    def hitsTarget(p: Probe) = p.launch.isDefined

    val allPossibleVelocities = 
      for 
        // somewhat reasonable ranges imo
        x <- (1 to xRange.min).filter(v => (1 to v).sum <= xRange.min).max to xRange.max
        y <- yRange.min until yRange.min.abs
      yield
        Probe(Vel(x, y), Pos(0, 0))

    allPossibleVelocities.filter(hitsTarget).size
