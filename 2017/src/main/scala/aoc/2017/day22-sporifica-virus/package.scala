package aoc.y2017
import aoc.utils.*

package object day22:
  def parse(data: Seq[String]) = data.map(_.toVector).toVector.toMatrix
  
  def collectFrom(mat: Matrix[Char]) = 
    mat
      .zipWithIndex
      .toVectors
      .flatten
      .collect { case ('#', i) => i -> Node.Infected }
      .toMap
      .withDefaultValue(Node.Clean)

  enum Node:
    case Clean, Weakened, Infected, Flagged
    def nextState = Node.values((this.ordinal + 1) % 4)

  case class Virus(pos: Pos2D, dir: Cardinal, infections: Int, grid: Map[Pos2D, Node]):
    import Node.*
  
    def move =   
      val infecting = !(grid contains pos)
      val newDir = if infecting then dir.left else dir.right
      Virus(
        pos move newDir,
        newDir,
        if infecting then infections + 1 else infections,
        if infecting then grid + (pos -> Infected) else grid - pos
      )

    def moveEvolved = 
      val newDir = grid(pos) match
        case Clean => dir.left
        case Weakened => dir
        case Infected => dir.right
        case Flagged => dir.reverse
      Virus(
        pos move newDir,
        newDir,
        if grid(pos) == Weakened then infections + 1 else infections,
        grid + (pos -> grid(pos).nextState),
      )