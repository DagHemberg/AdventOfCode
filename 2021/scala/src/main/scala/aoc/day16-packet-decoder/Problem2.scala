package aoc.day16
import aoc.utils.*
import scala.collection.mutable.ListBuffer

object Problem2 extends Solver("16", 2261L):
  def name = "Packet Decoder - Part 2"

  def packetTypeOperation(i: Long, lb: ListBuffer[Packet]) = i match
    case 0 => lb.map(_.value).sum
    case 1 => lb.map(_.value).product
    case 2 => lb.map(_.value).min
    case 3 => lb.map(_.value).max
    case 5 => if lb(0).value > lb(1).value then 1L else 0L
    case 6 => if lb(0).value < lb(1).value then 1L else 0L
    case 7 => if lb(0).value == lb(1).value then 1L else 0L
    
  def solve(data: Vector[String]) = 
    val inputs = data.map(_.map(h => f"${BigInt(h.toString, 16).toString(2).toInt}%04d").mkString)
    
    def generatePacket(str: String): Packet = 
      str.typeId match 
        case 4 => 
          val t = str.drop(6).sliding(5,5).toVector
          val cont = t.takeWhile(_.head == '1').map(_.tail) :+ t.dropWhile(_.head == '1').map(_.tail).head
          Packet(5 * cont.size + 6, str.versionId, cont.mkString.toDec, Vector.empty)
        
        case id => str.lengthId match
          case 0 =>
            val lb = ListBuffer.empty[Packet]
            var subPackets = str.drop(22).take(str.drop(7).take(15).toDec.toInt)
            
            while subPackets.size > 0 do
              val packet = generatePacket(subPackets)
              lb += packet
              subPackets = subPackets.drop(packet.size.toInt)
            
            Packet(22 + lb.map(_.size).sum, str.versionId, packetTypeOperation(id, lb), lb.toVector)
          
          case 1 => 
            var subPackets = str.drop(18)
            var subPacketsLeft = str.drop(7).take(11).toDec
            val lb = ListBuffer.empty[Packet]
            
            while subPacketsLeft > 0 do
              val packet = generatePacket(subPackets)
              lb += packet
              subPacketsLeft -= 1
              subPackets = subPackets.drop(packet.size.toInt)
            
            Packet(18 + lb.map(_.size).sum, str.versionId, packetTypeOperation(id, lb), lb.toVector)

    inputs.map(generatePacket).map(_.value).sum