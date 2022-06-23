package aoc.y2017
import aoc.utils.*

package object day20:
  val origin = Pos3D(0,0,0)

  case class Particle(id: Int, pos: Pos3D, vel: Pos3D, acc: Pos3D):
    def tick = copy(pos = pos + vel + acc, vel = vel + acc)

  def parse(data: Seq[String]) = data
    .zipWithIndex
    .map{ case (s"p=<$px,$py,$pz>, v=<$vx,$vy,$vz>, a=<$ax,$ay,$az>", i) => 
      Particle(
        i,
        Pos3D(px.toInt, py.toInt, pz.toInt),
        Pos3D(vx.toInt, vy.toInt, vz.toInt),
        Pos3D(ax.toInt, ay.toInt, az.toInt)
      )
    }
