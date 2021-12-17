package aoc.day16

import scala.collection.mutable.ListBuffer

case class Packet(size: Int, version: Int, value: Long, subPackets: Vector[Packet]):
  // for debugging
  override def toString = 
    if subPackets.isEmpty then s"{v$version :$value}" 
    else s"{v$version :$value ![${subPackets.mkString(", ")}]}"

def generateBinaryString(str: String) = str.map(hexChar => f"${BigInt(hexChar.toString, 16).toString(2).toInt}%04d").mkString

extension (str: String)
  def toDec = BigInt(str, 2).toString(10).toLong
  def versionId = str.take(6).sliding(3,3).toVector.head.toDec.toInt
  def typeId  = str.take(6).sliding(3,3).toVector.last.toDec
  def lengthId = str.drop(6).take(1).toInt

def generatePacket(str: String)(using packetTypeOperation: (Long, ListBuffer[Packet]) => Long): Packet =   
  str.typeId match 
    case 4 => 
      val valueSequences = str.drop(6).sliding(5,5).toVector
      val values = 
        valueSequences.takeWhile(_.head == '1').map(_.tail) 
        :+ valueSequences.dropWhile(_.head == '1').map(_.tail).head
      Packet(5 * values.size + 6, str.versionId, values.mkString.toDec, Vector.empty)
    
    case id => 
      str.lengthId match
        case 0 =>
          val lb = ListBuffer.empty[Packet]
          var subPackets = str.drop(22).take(str.drop(7).take(15).toDec.toInt)    

          while subPackets.size > 0 do
            val packet = generatePacket(subPackets)
            lb += packet
            subPackets = subPackets.drop(packet.size)

          Packet(22 + lb.map(_.size).sum, str.versionId, packetTypeOperation(id, lb), lb.toVector)
        
        case 1 => 
          val lb = ListBuffer.empty[Packet]
          var subPackets = str.drop(18)
          var subPacketsLeft = str.drop(7).take(11).toDec

          while subPacketsLeft > 0 do
            val packet = generatePacket(subPackets)
            lb += packet
            subPacketsLeft -= 1
            subPackets = subPackets.drop(packet.size)

          Packet(18 + lb.map(_.size).sum, str.versionId, packetTypeOperation(id, lb), lb.toVector)