package aoc.y2017
import aoc.utils.*

package object day19:
  enum Direction:
    case Up, Down, Left, Right
    def opposite = this match
      case Up    => Down
      case Down  => Up
      case Left  => Right
      case Right => Left

  import Direction.*

  def get(using grid: Matrix[(Char, Pos2D)]) = grid.apply.tupled

  def step(i: Pos2D, dir: Direction) = dir match
    case Up    => i.up
    case Down  => i.down
    case Left  => i.left
    case Right => i.right

  case class Packet(pos: Pos2D, dir: Direction):
    def move(using grid: Matrix[(Char, Pos2D)]) =  
      get(pos).head match
        case '+' =>
          val newPos = pos
            .neighboursOrthIn
            .filter(_.head != ' ')
            .filter(_ != get(step(pos, dir.opposite)))
            .head._2
          val newDir = dir match
            case Up | Down    => step(newPos, Left) match
              case `pos` => Right
              case _ => Left
            case Left | Right => step(newPos, Up) match
              case `pos` => Down
              case _ => Up
          Packet(newPos, newDir)
        case _ => Packet(step(pos, dir), dir)

  def first(using grid: Matrix[(Char, Pos2D)]) = 
    Packet(grid.row(0).find((c, _) => c == '|').map(_._2).get, Down)

  def path(using Matrix[(Char, Pos2D)]) = 
    (List.empty[Char], first).doUntil
      ((_, packet) => get(packet.pos).head == ' ')
      ((acc, packet) => (get(packet.pos).head :: acc, packet.move))
      .head.reverse.mkString
