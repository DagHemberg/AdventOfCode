package aoc.y2017
import aoc.utils.*

package object day20:
  val origin = (0, 0, 0)

  case class Particle(id: Int, pos: Pos3D, vel: Vec3, acc: Vec3):
    def tick = copy(pos = pos + vel + acc, vel = vel + acc)

  def parse(data: Seq[String]) = data
    .zipWithIndex
    .map{ case (s"p=<$px,$py,$pz>, v=<$vx,$vy,$vz>, a=<$ax,$ay,$az>", i) => 
      Particle(
        i,
        (px.toInt, py.toInt, pz.toInt),
        (vx.toInt, vy.toInt, vz.toInt),
        (ax.toInt, ay.toInt, az.toInt)
      )
    }
