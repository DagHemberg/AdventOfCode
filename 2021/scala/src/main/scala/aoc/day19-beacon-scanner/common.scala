package aoc.day19
import aoc.utils.*

case class Pos3D(x: Int, y: Int, z: Int): 
  override def toString = s"($x,$y,$z)"
  def toVector = Vector(x, y, z)
  def +(p: Pos3D) = Pos3D(x + p.x, y + p.y, z + p.z)
  def -(p: Pos3D) = Pos3D(x - p.x, y - p.y, z - p.z)
  def distance(p: Pos3D) = (x - p.x).abs + (y - p.y).abs + (z - p.z).abs

extension (vs: Vector[Int]) def toPos3D = Pos3D(vs(0), vs(1), vs(2))

case class Scanner(pos: Pos3D, beacons: Seq[Pos3D]):
  override def toString = s"\n--- Scanner at $pos ---\n${beacons.mkString("\n")}\n"

  def updatedOrigin(p: Pos3D) =
    copy(pos = pos - p, beacons = beacons.map(_ - p))

  def allRotations = 
    val newOrigins = rotations(pos.toVector)
    val newBeacons = beacons.map(b => rotations(b.toVector)).transpose
    newOrigins zip newBeacons map Scanner.apply

  def overlaps(other: Scanner) = (beacons intersect other.beacons).size >= 12

def align(s1: Scanner, s2: Scanner): Option[(Pos3D, Scanner)] =
  val ps = s1.beacons
  var found = false
  var i = 0
  var newP = Pos3D(0, 0, 0)
  var newS2 = s2
  while !found && i < ps.size do
    val p = ps(i)
    val newS1 = s1.updatedOrigin(p)
    val allS2 = s2.allRotations.flatMap(s => s.beacons.map(s.updatedOrigin))
    val potS2 = allS2.find(newS1.overlaps)
    if potS2.isDefined then
      found = true
      newS2 = potS2.get
      newP = p
    else i += 1

  if found then Some(newP, newS2) else None

def alignScanners(found: Seq[Scanner], remaining: Seq[Scanner]): Seq[Scanner] = 
  if remaining.isEmpty then found
  else 
    val thicc = Scanner(Pos3D(0, 0, 0), found.map(_.beacons.toSet).reduce(_ union _).toSeq)
    align(thicc, remaining.head) match
      case Some((p, s)) => alignScanners(found.map(_.updatedOrigin(p)) :+ s, remaining.tail)
      case None => alignScanners(found, remaining.tail :+ remaining.head)

def align(data: Vector[String]) = 
  val scanners = data
    .mkString("\n")
    .split("\n\n")
    .toVector
    .map(positions =>
      Scanner(
        Pos3D(0, 0, 0),
        positions
          .split("\n").tail.toVector
          .map { case s"$x,$y,$z" => Pos3D(x.toInt, y.toInt, z.toInt) }
      )
    )

  alignScanners(Seq(scanners.head), scanners.tail)
  
