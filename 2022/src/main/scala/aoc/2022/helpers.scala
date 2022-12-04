package problemutils

import math.Numeric.Implicits.infixNumericOps

// todo: move to problemutils repo once aoc is done

extension (s1: String) 
  def to(s2: String) = s1.toInt to s2.toInt

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
