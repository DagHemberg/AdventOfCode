package aoc.y2022.day14
import problemutils.*, extensions.*

import scala.util.parsing.combinator.*

case class Line(start: Pos2D, end: Pos2D):
  def between = 
    val (xMin, xMax) = (start.x min end.x, start.x max end.x)
    val (yMin, yMax) = (start.y min end.y, start.y max end.y)
    if start.x == end.x 
    then (yMin to yMax).map((_, start.x))
    else (xMin to xMax).map((start.y, _))

object Line extends RegexParsers:
  def int = raw"\d+".r ^^ { _.toInt }
  def coord = int ~ "," ~ int ^^ { case x ~ _ ~ y => (y, x) }
  def list = repsep(coord, "->")
  def getPositions(str: String) = parseAll(list, str).get
    .sliding(2)
    .flatMap(xs => Line(xs.head, xs.last).between)
    .toSet

def parse(data: List[String]) = data
  .map(Line.getPositions)
  .reduce(_ union _)
  
case class Grain(pos: Pos2D):
  def move(using occupied: Set[Pos2D], bottom: Int): Grain = 
    (pos.down, pos.dl, pos.dr) match
      case (d, _, _) if d.y > bottom => this
      case (d, _, _) if !occupied(d) => Grain(d)
      case (_, l, _) if !occupied(l) => Grain(l)
      case (_, _, r) if !occupied(r) => Grain(r)
      case _ => this

case class Environment(rocks: Set[Pos2D], grain: Grain, steps: Int)

def simulateUntil(lastYPos: Int)(using rocks: Set[Pos2D], bottom: Int) = 
  val init = Environment(rocks, Grain(1, 500), 0)
  
  init
    .doUntil(_.grain.pos.y == lastYPos) { env => 
      val finalPos = Grain(0, 500).converge(_.move(using env.rocks))
      Environment(env.rocks + finalPos.pos, finalPos, env.steps + 1)
    }.steps
