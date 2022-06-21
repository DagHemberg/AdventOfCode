package aoc.y2017
import aoc.utils.*

package object day19:
  def get(using grid: Matrix[(Char, (Int, Int))]) = grid.apply.tupled

  enum Direction:
    case Up, Down, Left, Right
    def opposite = this match
      case Up    => Down
      case Down  => Up
      case Left  => Right
      case Right => Left

  import Direction.*

  def step(i: (Int, Int), dir: Direction) = dir match
    case Up    => i.up
    case Down  => i.down
    case Left  => i.left
    case Right => i.right

  def next(index: (Int, Int), dir: Direction)(using grid: Matrix[(Char, (Int, Int))]) =
    val (r, c) = index
    grid(r, c) match
      case ('+', d) =>
        val newPos = index.neighboursOrthIn
          .filter(_._1 != ' ')
          .filter(_ != get(step(index, dir.opposite)))
          .head._2
        val newDir = dir match
          case Up | Down    => step(newPos, Left) match
            case `index` => Right
            case _ => Left
          case Left | Right => step(newPos, Up) match
            case `index` => Down
            case _ => Up
        (newPos, newDir)
      case (c, d) => (step(index, dir), dir)

  extension (pair: ((Int, Int), Direction)) 
    def move(using Matrix[(Char, (Int, Int))]) = next.tupled(pair)

  def first(using grid: Matrix[(Char, (Int, Int))]) = grid.row(0).find((c, _) => c == '|').map(_._2).get  
  
  def path(using grid: Matrix[(Char, (Int, Int))]) = 
    (List.empty[Char], (first, Direction.Down)).doUntil
      { case (_, (i, _)) => get(i)._1 == ' ' }
      { case (acc, (i, d)) => (get(i).head :: acc, (i, d).move) }
      .head.reverse.mkString
