package aoc.y2022.day23
import problemutils.*, extensions.*

import Cardinal.*

type Dirs = List[Cardinal]

def parse(data: List[String]) = data
  .map(_.toSeq)
  .toMatrix
  .zipWithIndex
  .toVector
  .collect:
    case ('#', i) => i
  .toSet
  .pipe(elves => Grove(elves, 0))

val allDirs = List(
  List(North, NorthWest, NorthEast),
  List(South, SouthWest, SouthEast),
  List(West, NorthWest, SouthWest),
  List(East, NorthEast, SouthEast)  
)

val mat = Vector
  .iterate(allDirs, 4)(dirs => dirs.tail :+ dirs.head)
  .toMatrix

def dirsAtRound(round: Int) = mat.rows(round % 4).toList

case class Grove(elves: Set[Pos2D], round: Int):
  override def toString = asMatrix.toString
  def asMatrix = 
    val (minC, maxC) = elves.map(_.x).pipe(xs => (xs.min, xs.max))
    val (minR, maxR) = elves.map(_.y).pipe(ys => (ys.min, ys.max))
    Matrix(maxR - minR + 1, maxC - minC + 1) { (r, c) => 
      if elves.contains((r + minR, c + minC)) then '#' else '.'
    }

  override def equals(x: Any): Boolean = x match
    case that: Grove => this.elves == that.elves
    case _ => false

  def proposePositions(using allDirs: List[Dirs]) = 
    elves.map { pos =>
      def propose(dirs: Dirs) = 
        if dirs.exists(dir => elves.contains(pos move dir)) then None
        else Some(pos move dirs.head)

      val proposedNewPos = 
        if pos.neighbours.filter(elves.contains).isEmpty 
        then None
        else allDirs
          .tail
          .foldLeft(propose(allDirs.head))((pos, newDir) => pos orElse propose(newDir))

      pos -> proposedNewPos
    }.map((o, n) => o -> n.getOrElse(o))

  def move = 
    given List[Dirs] = dirsAtRound(round)
    val proposed = proposePositions
    val occurances = proposed.groupMapReduce(snd)(_ => 1)(_ + _)
    
    proposed
      .map((o, n) => if occurances(n) > 1 then o else n)
      .toSet
      .pipe(copy(_, round + 1))
