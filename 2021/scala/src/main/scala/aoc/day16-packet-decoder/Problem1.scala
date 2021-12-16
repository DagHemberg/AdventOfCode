package aoc.day16
import aoc.utils.*
import scala.collection.mutable.ListBuffer

object Problem1 extends Solver("16", 227L):
  def name = "Packet Decoder - Part 1"
  def solve(data: Vector[String]) = 
    val inputs = data.map(_.map(h => f"${BigInt(h.toString, 16).toString(2).toInt}%04d").mkString)

    def generatePacket(str: String): Packet = 
      str.typeId match 
        case 4 => 
          val t = str.drop(6).sliding(5,5).toVector
          val cont = t.takeWhile(_.head == '1').map(_.tail) :+ t.dropWhile(_.head == '1').map(_.tail).head
          Packet(5 * cont.size + 6, str.versionId, cont.mkString.toDec, Vector.empty)
        
        case _ => str.lengthId match
          case 0 =>
            val lb = ListBuffer.empty[Packet]
            var subPackets = str.drop(22).take(str.drop(7).take(15).toDec.toInt)
            
            while subPackets.size > 0 do
              val packet = generatePacket(subPackets)
              lb += packet
              subPackets = subPackets.drop(packet.size.toInt)
            
            Packet(22 + lb.map(_.size).sum, str.versionId, lb.map(_.value).sum, lb.toVector)
          
          case 1 => 
            var subPacketsLeft = str.drop(7).take(11).toDec
            var subPackets = str.drop(18)
            val lb = ListBuffer.empty[Packet]
            
            while subPacketsLeft > 0 do
              val packet = generatePacket(subPackets)
              lb += packet
              subPackets = subPackets.drop(packet.size.toInt)
              subPacketsLeft -= 1
            
            Packet(18 + lb.map(_.size).sum, str.versionId, lb.map(_.value).sum, lb.toVector)
    
    val packets = inputs.map(generatePacket)

    def sumVersions(packet: Packet): Long = 
      if packet.subPackets.isEmpty then packet.version
      else packet.version + packet.subPackets.map(sumVersions).sum

    packets.map(sumVersions).sum.debug