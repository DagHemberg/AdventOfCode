package problemutils

import math.Numeric.Implicits.infixNumericOps
import scala.util.chaining

// todo: move to problemutils repo once aoc is done

extension [A](a: A)
  def tap[U](f: A => U) = 
    f(a)
    a

  def pipe[B](f: A => B) = f(a)

  def logAttrIf[B](p: A => Boolean)(f: A => B) = 
    if p(a) then println(f(a))
    a

import problemutils.extensions.*
extension (pos: Pos2D)
  def %(other: Pos2D) = (math.floorMod(pos.y, other.y), math.floorMod(pos.x, other.x))

extension [A] (xs: IterableOnce[A])
  def sumBy[B](f: A => B)(using num: Numeric[B]) = 
    xs.iterator.foldLeft(num.zero)((sum, x) => sum + f(x))

extension [A] (xs: Seq[A])
  def split(elem: A): List[Seq[A]] = 
    def rec(remaining: Seq[A], acc: List[Seq[A]]): List[Seq[A]] = 
      remaining.indexOf(elem) match
        case -1 => remaining :: acc
        case i => rec(remaining.drop(i + 1), remaining.take(i) :: acc)
    rec(xs, Nil).reverse

extension [A] (mat: Matrix[A])
  def find(pred: A => Boolean) = mat.toVector.find(pred)
  def indexWhere(pred: A => Boolean) = mat.zipWithIndex.find((a, b) => pred(a)).map(_._2)

extension [A, B] (map: Map[A, B])
  def invert = map
    .toSeq
    .map(_.swap)
    .foldLeft(Map.empty[B, Set[A]]):
      case (acc, (key, value)) => 
        if acc isDefinedAt key then acc + (key -> (acc(key) + value))
        else acc + (key -> Set(value))

def fst[A, B](tup: (A, B)) = tup._1
def snd[A, B](tup: (A, B)) = tup._2
