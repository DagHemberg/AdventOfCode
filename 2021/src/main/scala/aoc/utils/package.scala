package aoc
import math.Numeric.Implicits.infixNumericOps
import Console.*

package object utils:

  // extensions
  extension [A](a: A)
    def debug = 
      println(s"[${YELLOW}*${RESET}] $a")
      a
    
    def log(color: String = "cyan") =
      val col = color.toLowerCase match
        case "cyan" => CYAN
        case "red" => RED
        case "green" => GREEN
        case "yellow" => YELLOW
        case "blue" => BLUE
        case "magenta" => MAGENTA
        case _ => CYAN
      println(s"[${col}+${RESET}] $a")
      a

    def logAttr[B](f: A => B) = 
      println(s"[${CYAN}+${RESET}] ${f(a)}")
      a

    def warn(f: A => Boolean) = 
      if f(a) then println(s"[${RED}!${RESET}] $a")
      a

  extension [A](xs: IndexedSeq[A])
    def middle(implicit ord: Ordering[A]) = xs.sorted.apply(xs.size / 2)

  extension [A: Numeric](xs: Vector[A])
    def average = xs.sum.toDouble / xs.size
    def toPos3D = 
      require(xs.size == 3, s"Vector must have 3 elements, but has ${xs.size}")
      val xd = xs.map(_.toDouble.round.toInt)
      Pos3D(xd(0), xd(1), xd(2))

    infix def dot (ys: Vector[A]) = 
      require(xs.size == ys.size, "Vectors must be the same size")
      (xs zip ys map (_ * _)).sum

    infix def cross (ys: Vector[A]) = 
      require(xs.size == 3 && ys.size == 3, "Cross product only defined for 3D vectors")
      Vector(
        xs(1) * ys(2) - xs(2) * ys(1), 
        xs(2) * ys(0) - xs(0) * ys(2), 
        xs(0) * ys(1) - xs(1) * ys(0)
      )

    def magnitude = math.sqrt((xs dot xs).toDouble)
    def normalized = xs.map(_.toDouble * (1.0 / xs.magnitude))

  // small case classes
  case class Pos(x: Int, y: Int):
    override def toString = s"($x,$y)"
    def transpose = Pos(y, x)
    def tuple = (x, y)
    def +(p: Pos) = Pos(x + p.x, y + p.y)
    def +(p: (Int, Int)) = Pos(x + p._1, y + p._2)
    def -(p: Pos) = Pos(x - p.x, y - p.y)
    def -(p: (Int, Int)) = Pos(x - p._1, y - p._2)
    def manhattan(p: Pos) = (x - p.x).abs + (y - p.y).abs
    
  case class Pos3D(x: Int, y: Int, z: Int):
    override def toString = s"($x,$y,$z)"
    def tuple = (x, y, z)
    def toVector = Vector(x, y, z)
    def +(p: Pos3D) = Pos3D(x + p.x, y + p.y, z + p.z)
    def +(p: (Int, Int, Int)) = Pos3D(x + p._1, y + p._2, z + p._3)
    def -(p: Pos3D) = Pos3D(x - p.x, y - p.y, z - p.z)
    def -(p: (Int, Int, Int)) = Pos3D(x - p._1, y - p._2, z - p._3)
    def manhattan(p: Pos3D) = (x - p.x).abs + (y - p.y).abs + (z - p.z).abs

  case class Line(start: Pos, end: Pos)

  case class TimedEval[A](duration: Double, result: A)
  object TimedEval:
    def time[A](block: => A): TimedEval[A] =
      val start = System.nanoTime()
      val result = block
      val duration = (System.nanoTime() - start) / 1E9
      TimedEval(duration, result)