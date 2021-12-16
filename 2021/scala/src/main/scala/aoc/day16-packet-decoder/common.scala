package aoc.day16

case class Packet(size: Int, version: Int, value: Long, subPackets: Vector[Packet]):
  // for debugging
  override def toString = 
    if subPackets.isEmpty then s"[v$version :$value]" 
    else s"[v$version :$value ![${subPackets.mkString(", ")}]]"

extension (str: String)
  def toDec = BigInt(str, 2).toString(10).toLong
  def versionId = str.take(6).sliding(3,3).toVector.head.toDec.toInt
  def typeId  = str.take(6).sliding(3,3).toVector.last.toDec
  def lengthId = str.drop(6).take(1).toInt