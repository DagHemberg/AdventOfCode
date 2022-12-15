package aoc.y2022.day13
import problemutils.*, extensions.*

import scala.util.parsing.combinator.*

case class Packet(data: List[Int | Packet]) extends Ordered[Packet]:
  override def compare(that: Packet): Int = 
    def comp(a: Int | Packet, b: Int | Packet): Int = (a, b) match
      case (a: Int, b: Int) => a - b
      case (a: Packet, b: Int) => a compare Packet(b)
      case (a: Int, b: Packet) => Packet(a) compare b
      case (a: Packet, b: Packet) => 
        val z = a.data zip b.data
        val r = z.foldLeft(0) { case (curr, (e1, e2)) => 
          if curr == 0 then comp(e1, e2) else curr
        }
        if r == 0 then a.data.size - b.data.size else r
        
    comp(this, that)        

object Packet extends RegexParsers:
  def apply(data: (Int | Packet)*): Packet = Packet(List(data*))
  
  private def int = raw"\d+".r ^^ (_.toInt)
  @annotation.nowarn
  private def packet: Parser[Packet] = 
    "[" ~> repsep(int | packet, ",") <~ "]" ^^ {
      case xs: List[Int | Packet] => Packet(xs)
    }
  
  def from(str: String) = parseAll(packet, str).get

def parse(data: List[String]) = data
  .split("")
  .map(_.map(Packet.from))
