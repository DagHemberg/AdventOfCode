package aoc.y2022.day24
import problemutils.*, extensions.*

import Cardinal.*

def parse(data: List[String]) = data
  .map(_.toSeq)
  .toMatrix
  .slice(1, 1)(data.head.size - 2, data.size - 2)

case class Blizzard(dir: Cardinal, pos: Pos2D):
  override def toString = s"$pos moving $dir"
  def move(using dims: (Int, Int)) = copy(pos = (pos move dir) % dims)

case class Basin(player: Pos2D, steps: Int)
(using blizzards: Vector[Set[Blizzard]], dims: Vec2, mod: Int, mat: Matrix[Pos2D]):
  def fromHere: Set[Basin] = 
    val newBlizzards = blizzards(steps).map(_.move)
    player
      .neighboursOrthIn
      .appended(player)
      .appendedAll(if player == (0, 0) then Set((-1, 0)) else Set.empty)
      .flatMap { p =>
        if newBlizzards.map(_.pos).contains(p) then None
        else Some(Basin(p, (steps + 1) % mod))
      }.toSet
