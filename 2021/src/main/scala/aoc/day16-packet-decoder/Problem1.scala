package aoc.day16
import aoc.utils.*
import scala.collection.mutable.ListBuffer

object Problem1 extends Solver("16", 227L):
  def name = "Packet Decoder - Part 1"
  def solve(data: Vector[String]) = 
    val packetStrings = data.map(generateBinaryString)

    // we only care about the packet version here, so the 
    // value can be set to 0 for all non-version-4-packets
    given ((Long, ListBuffer[Packet]) => Long) = (_, _) => 0L

    val packets = packetStrings.map(generatePacket)

    def sumVersions(packet: Packet): Long = 
      if packet.subPackets.isEmpty then packet.version
      else packet.version + packet.subPackets.map(sumVersions).sum

    packets.map(sumVersions).sum
