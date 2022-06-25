package aoc.y2017
import aoc.utils.*

package object day24:
  case class Component(port1: Int, port2: Int, strength: Int, others: Set[Component], length: Int = 1):
    override def toString = s"[$port1/$port2] @ $strength"
    val ports = Set(port1, port2)
    def join(other: Component) = 
      val newStrength = strength + other.strength
      val newOthers = others - other
      val newLength = length + 1
      if      port2 == other.port1 then Component(port2, other.port2, newStrength, newOthers, newLength)
      else if port2 == other.port2 then Component(port2, other.port1, newStrength, newOthers, newLength)
      else this

  def parse(data: Seq[String]) = data.map {
    case s"$port1/$port2" => Component(port1.toInt, port2.toInt, port1.toInt + port2.toInt, Set.empty)
  }.toSet

  def buildBridges(components: Set[Component]) = components
    .map(c => c.copy(others = components - c))
    .filter(_.port1 == 0)
    .converge(_.flatMap(c => c.others.map(c.join)))