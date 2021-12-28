package aoc.day22
import aoc.utils.*

case class Dim(min: Long, max: Long):
  def size: Long = max - min + 1
  def valid = min <= max

case class Cuboid(x: Dim, y: Dim, z: Dim, sign: Int):
  def volume = x.size * y.size * z.size * sign
  def intersect(other: Cuboid): Option[Cuboid] =
    val dimX = Dim(x.min max other.x.min, x.max min other.x.max)
    val dimY = Dim(y.min max other.y.min, y.max min other.y.max)
    val dimZ = Dim(z.min max other.z.min, z.max min other.z.max)
    if Seq(dimX, dimY, dimZ).forall(_.valid)
    then Some(Cuboid(dimX, dimY, dimZ, -other.sign))
    else None 

def cuboids(using data: Vector[String]) =

  val cbs = data
    .map { case s"$sign x=$xmin..$xmax,y=$ymin..$ymax,z=$zmin..$zmax" =>
      Cuboid(
        Dim(xmin.toLong, xmax.toLong),
        Dim(ymin.toLong, ymax.toLong),
        Dim(zmin.toLong, zmax.toLong),
        if sign == "on" then 1 else -1
      )
    }

  cbs.tail.foldLeft(Vector(cbs.head))((all, cuboid) =>
    val intersections = all ++ all.flatMap(cuboid.intersect)
    if cuboid.sign > 0 then intersections :+ cuboid else intersections
  )
