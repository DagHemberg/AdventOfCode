package aoc.day22
import aoc.utils.*

case class Dim(min: Long, max: Long):
  def size: Long = max - min + 1

case class Cuboid(x: Dim, y: Dim, z: Dim, sign: Int):
  def size = x.size * y.size * z.size * sign
  def valid = x.min <= x.max && y.min <= y.max && z.min <= z.max

  def intersect(other: Cuboid): Cuboid =
    Cuboid(
      Dim(x.min max other.x.min, x.max min other.x.max),
      Dim(y.min max other.y.min, y.max min other.y.max),
      Dim(z.min max other.z.min, z.max min other.z.max),
      -other.sign
    )

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
    val intersections = all ++ all.map(cuboid.intersect).filter(_.valid)
    if cuboid.sign > 0 then intersections :+ cuboid else intersections
  )
