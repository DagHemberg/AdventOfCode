package aoc.y2022.day13
import problemutils.*, extensions.*

import scala.util.parsing.combinator.*

type Element = Int | Packet

case class Packet(data: List[Element]) extends Ordered[Packet]:
  def compare(that: Packet): Int = 
    def cmp(a: Element, b: Element): Int = (a, b) match
      case (a: Int, b: Int) => a - b
      case (a: Packet, b: Int) => a compare Packet(b)
      case (a: Int, b: Packet) => Packet(a) compare b
      case (a: Packet, b: Packet) => 
        val c = a.data
          .zip(b.data)
          .foldLeft(0)((curr, elems) => if curr == 0 then cmp.tupled(elems) else curr)
        if c == 0 then a.data.size - b.data.size else c
        
    cmp(this, that)

object Packet extends RegexParsers:
  def apply(data: Element*): Packet = Packet(List(data*))
  
  private def int = raw"\d+".r ^^ (_.toInt)
  @annotation.nowarn
  private def packet: Parser[Packet] = 
    "[" ~> repsep(int | packet, ",") <~ "]" ^^ { case xs: List[Element] => Packet(xs) }
  
  def parse(str: String) = parseAll(packet, str).get

def parse(data: List[String]) = data
  .split("")
  .map(_.map(Packet.parse))
