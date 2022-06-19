package aoc.y2017
import aoc.utils.*

package object day15:
  def parse(data: Seq[String]) = data.map { case s"Generator $name starts with $value" =>
    value.toLong
  }

  val aFac = 16807
  val bFac = 48271

  def next(value: Long, factor: Long) = (value * factor).toLong % Int.MaxValue
  def judge(a: Long, b: Long) = if (a & 0xffff) == (b & 0xffff) then 1 else 0
  
  def nextMult(value: Long, fac: Long, mult: Long): Long =
    next(value, fac).doUntil(_ % mult == 0)(n => next(n, fac))

  def generate
    (a: Long, b: Long, amount: Int, n1: Int = 1, n2: Int = 1) =
    
    (a, b, 0, 0).doUntil((_, _, _, i) => i >= amount)((a, b, count, i) =>
      (
        nextMult(a, aFac, n1),
        nextMult(b, bFac, n2),
        count + judge(a, b),
        i + 1
      )
    )._3
