package aoc.day16
import aoc.utils.*
import scala.collection.mutable.ListBuffer

object Problem2 extends Solver("16", 2261L):
  def name = "Packet Decoder - Part 2"
  def solve(data: Vector[String]) = 
    val packetStrings = data.map(generateBinaryString)

    def packetTypeOperation(i: Long, lb: ListBuffer[Packet]) = i match
      case 0 => lb.map(_.value).sum
      case 1 => lb.map(_.value).product
      case 2 => lb.map(_.value).min
      case 3 => lb.map(_.value).max
      case 5 => if lb(0).value > lb(1).value then 1L else 0L
      case 6 => if lb(0).value < lb(1).value then 1L else 0L
      case 7 => if lb(0).value == lb(1).value then 1L else 0L

    given ((Long, ListBuffer[Packet]) => Long) = packetTypeOperation
    
    packetStrings.map(generatePacket).map(_.value).sum
