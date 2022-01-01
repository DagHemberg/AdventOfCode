package aoc.day13
import aoc.utils.*

case class Command(dir: String, value: Int)

def points(using data: Vector[String]) = data
  .takeWhile(_.nonEmpty)
  .map { case s"$x,$y" => Pos(x.toInt, y.toInt) }
  .toSet

def commands(using data: Vector[String]) = data
  .reverse
  .takeWhile(_.nonEmpty)
  .reverse
  .map { case s"fold along $dir=$value" => Command(dir, value.toInt) }

def fold(points: Set[Pos], commands: Vector[Command]) = 
  commands.foldLeft(points)((collectedPoints, command) => command match
    case Command("x", n) => collectedPoints.map(p => if p.x < n then p else Pos(2 * n - p.x, p.y))
    case Command("y", n) => collectedPoints.map(p => if p.y < n then p else Pos(p.x, 2 * n - p.y)))
