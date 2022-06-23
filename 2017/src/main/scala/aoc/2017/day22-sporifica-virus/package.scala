package aoc.y2017
import aoc.utils.*

package object day22:
  def parse(data: Seq[String]) = data.map(_.toVector).toVector.toMatrix
  
  def collectFrom(mat: Matrix[Char]) = 
    mat
      .zipWithIndex
      .toVectors
      .flatten
      .collect { case ('#', i) => Pos(i) -> Node.Infected }
      .toMap
      .withDefaultValue(Node.Clean)

  enum Node:
    case Clean, Weakened, Infected, Flagged
    def nextState = Node.values((this.ordinal + 1) % 4)

  enum Direction:
    case Up, Right, Down, Left 
    def cw = Direction.values((this.ordinal + 1) % 4)
    def ccw = Direction.values((this.ordinal + 3) % 4)
    def opposite = Direction.values((this.ordinal + 2) % 4)

  extension (p: Pos) def move(dir: Direction) = 
    import Direction.*
    dir match
      case Up => p + Pos(-1, 0)
      case Right => p + Pos(0, 1)
      case Down => p + Pos(1, 0)
      case Left => p + Pos(0, -1)

  case class Virus(pos: Pos, dir: Direction, infections: Int, grid: Map[Pos, Node]):
    import Node.*
  
    def move =   
      val infecting = !(grid contains pos)
      val newDir = if infecting then dir.ccw else dir.cw
      val newPos = pos move newDir    
      Virus(
        newPos,
        newDir,
        if infecting then infections + 1 else infections,
        if infecting then grid + (pos -> Infected) else grid - pos
      )

    def moveEvolved = 
      val newDir = grid(pos) match
        case Clean => dir.ccw
        case Weakened => dir
        case Infected => dir.cw
        case Flagged => dir.opposite
      val newPos = pos move newDir
      Virus(
        newPos,
        newDir,
        if grid(pos) == Weakened then infections + 1 else infections,
        grid + (pos -> grid(pos).nextState),
      )