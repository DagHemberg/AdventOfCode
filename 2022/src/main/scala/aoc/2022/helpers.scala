package problemutils

import math.Numeric.Implicits.infixNumericOps
import scala.util.chaining

// todo: move to problemutils repo once aoc is done

extension [A](a: A)
  def tap[U](f: A => U) = 
    f(a)
    a

  def pipe[B](f: A => B) = f(a)

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