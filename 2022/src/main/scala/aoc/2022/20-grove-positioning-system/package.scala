package aoc.y2022.day20
import problemutils.*, extensions.*

type Size = Int

extension (ls: Seq[Vec2]) 
  def moveDigit(elem: Vec2)(using Size) = 
    val prev = ls.indexOf(elem)
    val List(before, after) = ls.split(elem)
    val post = prev jump fst(elem)
    if post == prev then ls
    else if post < prev 
    then before.take(post).appended(elem).appendedAll(before.drop(post)).appendedAll(after)
    else before.appendedAll(after.take(post - prev)).appended(elem).appendedAll(after.drop(post - prev))

extension (a: Int) 
  def jump(b: Int)(using size: Int) = 
    if a + b <= 0 || a + b >= size then math.floorMod(a + b, size - 1) match
      case 0 => size - 1
      case s if s == size - 1 => 0
      case other => other
    else math.floorMod(a + b, size)

def subOne(n: Int) = n - 1
