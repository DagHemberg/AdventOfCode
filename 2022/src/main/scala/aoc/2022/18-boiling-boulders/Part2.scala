package aoc.y2022.day18
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(18, 2022)(2)(58):
  def name = "Boiling Boulders - Part 2"
  def solve(data: List[String]) = 
    val cubes = parse(data)

    val xs = cubes.map(_.x)
    val ys = cubes.map(_.y)
    val zs = cubes.map(_.z)

    val (minX, maxX) = (xs.min - 1, xs.max + 1)
    val (minY, maxY) = (ys.min - 1, ys.max + 1)
    val (minZ, maxZ) = (zs.min - 1, zs.max + 1)

    val boundary = (minX, minY, minZ).expand { from =>
      from.adjacent.filter { case coord @ (x, y, z) =>
        !cubes.contains(coord) &&
        x >= minX && x <= maxX &&
        y >= minY && y <= maxY &&
        z >= minZ && z <= maxZ
      }
    }

    cubes.sumBy(_.adjacent.count(boundary.contains))