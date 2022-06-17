package aoc.y2017
import aoc.utils.*

package object day13:
  enum Dir:
    case Up, Down
    def movement = this match
      case Up   => -1
      case Down => 1

  case class Scanner(range: Int, position: Int, direction: Dir):
    import Dir.*
    override def toString = s"[$position / $range going $direction]"
    def move = (position, direction) match
      case (d, Up) if position == 1 => copy(position = 2, direction = Down)
      case (d, Down) if d == range  => copy(position = d - 1, direction = Up)
      case (d, _)                   => copy(position = d + direction.movement)

  def parse(data: Seq[String]) = data.map { 
    case s"$pos: $range" => pos.toInt -> Scanner(range.toInt, 1, Dir.Up)
  }.toMap

  extension (scs: Map[Int, Scanner]) def tick = scs.map(_ -> _.move)

  case class Rocket(scanners: Map[Int, Scanner], depth: Int = 0, caughtAt: List[(Int, Int)] = Nil):
    val severity = caughtAt.map(_ * _).sum
    val notCaught = caughtAt.isEmpty
    lazy val passedThrough = depth > scanners.keys.max
    def fly = Rocket(
      scanners.tick,
      depth + 1,
      if scanners.isDefinedAt(depth) && scanners(depth).position == 1
        then (depth -> scanners(depth).range) :: caughtAt
        else caughtAt
    )
