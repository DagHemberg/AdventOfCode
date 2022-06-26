package aoc
import math.Numeric.Implicits.infixNumericOps
import math.Integral.Implicits.infixIntegralOps
import math.Fractional.Implicits.infixFractionalOps
import Console.*

package object utils:
  type Pos2D = (Int, Int)
  type Vec3 = (Int, Int, Int)

  // WIP DIRECTION CLASS(ES)
  // trait Cardinal(x: Int, y: Int):
  //   def move(pos: Pos2D) = (pos.x + x, pos.y + y)

  // trait Hex(q: Int, r: Int, s: Int):
  //   def move(vec: Vec3) = (vec.q + q, vec.r + r, vec.s + s)

  // enum Direction:
  //   case North     extends Direction with Cardinal(0, -1) with Hex(0, -1, 1)
  //   case NorthEast extends Direction with Cardinal(1, -1) with Hex(1, -1, 0)
  //   case East      extends Direction with Cardinal(1, 0)
  //   case SouthEast extends Direction with Cardinal(1, 1) with Hex(1, 0, -1) 
  //   case South     extends Direction with Cardinal(0, 1) with Hex(0, 1, -1) 
  //   case SouthWest extends Direction with Cardinal(-1, 1) with Hex(-1, 1, 0)
  //   case West      extends Direction with Cardinal(-1, 0)
  //   case NorthWest extends Direction with Cardinal(-1, -1) with Hex(-1, 0, 1)
    
  //   import Direction.*
  //   def left = fromOrdinal(this.ordinal + 2 % 8)
  //   def right = fromOrdinal(this.ordinal + 6 % 8)
  //   def reverse = fromOrdinal(this.ordinal + 4 % 8)
  //   def clockwise = fromOrdinal(this.ordinal + 1 % 8)
  //   def counterclockwise = fromOrdinal(this.ordinal + 7 % 8)

  extension [A](a: A)
    /** Logs any object `a` in the console and then returns the object without modifying it. */
    def log = 
      println(s"[${YELLOW}*${RESET}] $a")
      a

    /** Logs any attribute of object `a` in the console and then returns the object without modifying it. */
    def logAttr[B](f: A => B) = 
      println(s"[${YELLOW}*${RESET}] ${f(a)}")
      a

    /** Logs any object `a` in the console without the "[*]" prefix and then returns the object without modifying it. */
    def lg = 
      println(a)
      a
    
    /** Prints an empty line in the console and returns the object without modifying it. Useful for debugging. */
    def space = 
      println()
      a

    /** Logs any object `a` in the console *if* the condition `p` is satisfied and then returns the object without modifying it. */
    def warn(p: A => Boolean) = 
      if p(a) then println(s"[${RED}!${RESET}] $a")
      a

    /** Similar to `log`, but takes a color parameter. */
    def logCol(color: String = "yellow") =
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

    /** Uses a [[scala.collection.immutable.LazyList]] to apply any function `A => A` on any object `n` times. */
    def iterate(f: A => A)(n: Int): A = 
      if n <= 0 then a
      else f(a).iterate(f)(n - 1)

    /** Recursively applies a function `f: A => A` on any object `a` until the predicate `p` is satisfied.
     */
    def doUntil(p: A => Boolean)(f: A => A): A = 
      if p(a) then a
      else f(a).doUntil(p)(f)

    /** Recursively applies a function `f: A => A` on any object `a` until `f(a)` is equal to `a`. Shorthand for `doUntil(_ == f(a))(f)`.
     */
    def converge(f: A => A): A = 
      val fa = f(a)
      if a == fa then a
      else fa.converge(f)

  extension [A](xs: IndexedSeq[A])
    /** Zips two sequences and applies a function on the resulting tuples. Functionally equivalent to `.zip(...).map(...)`. */
    def zipWith[B, C](ys: IndexedSeq[B])(f: (A, B) => C) =
      (xs zip ys).map(f.tupled)

  extension [A: Numeric](xs: IndexedSeq[A])
    def average = xs.sum.toDouble / xs.size
    def median(using Ordering[A]) = 
      val sorted = xs.sorted
      val half = xs.size / 2
      if xs.size % 2 == 0 then (sorted(half) + sorted(half - 1)).toDouble / 2
      else sorted(half).toDouble
    def rms = math.sqrt(xs.map(x => x * x).sum.toDouble / xs.size)

  extension [A: Numeric](xs: Vector[A])
    def toPos3D = 
      require(xs.size == 3)
      Pos3D(xs(0).toInt, xs(1).toInt, xs(2).toInt)

    /** Computes the [dot product](https://en.wikipedia.org/wiki/Dot_product) of 2 vectors of the same length. */
    infix def dot (ys: Vector[A]) = 
      require(xs.size == ys.size, "Vectors must be the same size")
      (xs zip ys map (_ * _)).sum

    /** Computes the [cross product](https://en.wikipedia.org/wiki/Cross_product) of 2 vectors of length 3. */
    infix def cross (ys: Vector[A]) = 
      require(xs.size == 3 && ys.size == 3, "Cross product only defined for 3D vectors")
      Vector(
        xs(1) * ys(2) - xs(2) * ys(1), 
        xs(2) * ys(0) - xs(0) * ys(2), 
        xs(0) * ys(1) - xs(1) * ys(0)
      )

    /** Returns the [magnitude](https://en.wikipedia.org/wiki/Magnitude_(mathematics)#Vector_spaces) of the vector. */
    def magnitude = math.sqrt((xs dot xs).toDouble)

    /** Returns a [normalized](https://en.wikipedia.org/wiki/Unit_vector) version of the vector. */
    def normalized = xs.map(_.toDouble * (1.0 / xs.magnitude))

  extension (str: String)
    def words = str.split("\\s+").toList
    def lines = str.split("\n").toList
    def padLeftTo(n: Int, char: Char) = str.reverse.padTo(n, char).reverse
    def findAllWith(regex: String) = regex.r.findAllIn(str).toList
    def findAllMatchWith(regex: String) = regex.r.findAllMatchIn(str).toList
    def findWith(regex: String) = regex.r.findFirstIn(str)
    def findMatchWith(regex: String) = regex.r.findFirstMatchIn(str)

  extension (b: Boolean)
    def toInt = if b then 1 else 0

  // would put in Matrix.scala but the compiler complained
  extension [A: Numeric](mat: Matrix[A])
    def +(other: Matrix[A]): Matrix[A] = mat zip other map (_ + _)
    def -(other: Matrix[A]): Matrix[A] = mat zip other map (_ - _)

  extension (tup: Pos2D)
    def transpose = (tup._2, tup._1)
    
    def row = tup._1
    def col = tup._2
    def x = tup._1
    def y = tup._2

    def +(other: Pos2D) = (tup._1 + other._1, tup._2 + other._2)
    def -(other: Pos2D) = (tup._1 - other._1, tup._2 - other._2)

    def up = (tup.row - 1, tup.col)
    def down = (tup.row + 1, tup.col)
    def left = (tup.row, tup.col - 1)
    def right = (tup.row, tup.col + 1)
    def ul = (tup.row - 1, tup.col - 1)
    def ur = (tup.row - 1, tup.col + 1)
    def dl = (tup.row + 1, tup.col - 1)
    def dr = (tup.row + 1, tup.col + 1)

    def neighbours: List[Pos2D] = List(tup.up, tup.down, tup.left, tup.right, tup.ul, tup.ur, tup.dl, tup.dr)
    def neighboursOrth = List(tup.up, tup.left, tup.right, tup.down)
    def neighboursDiag = List(tup.ul, tup.ur, tup.dl, tup.dr)

    private def outsideFilter[A](list: List[Pos2D])(using mat: Matrix[A]) = 
      list filterNot mat.indexOutsideBounds map mat.apply

    def neighboursIn[A](using mat: Matrix[A]) = tup.outsideFilter(tup.neighbours)
    def neighboursOrthIn[A](using mat: Matrix[A]) = tup.outsideFilter(tup.neighboursOrth)
    def neighboursDiagIn[A](using mat: Matrix[A]) = tup.outsideFilter(tup.neighboursDiag)

    private def toVector = Vector(tup._1, tup._2)

    def distance(other: Pos2D) = 
      (tup - other).toVector.magnitude

  extension (v: Vec3)
    def x = v._1
    def y = v._2
    def z = v._3

    def q = v._1
    def r = v._2
    def s = v._3

    def toVector = Vector(v.x, v.y, v.z)
    def +(other: Vec3) = (v.x + other.x, v.y + other.y, v.z + other.z)
    def -(other: Vec3) = (v.x - other.x, v.y - other.y, v.z - other.z)

    def distance(other: Vec3) = (v - other).toVector.magnitude
    def manhattan(other: Vec3) = (v - other).toVector.map(math.abs).sum

  /** Represents a position in 2D space */
  @deprecated("Use (Int, Int) / Pos2D instead", "0.2")
  case class Pos(x: Int, y: Int):
    /** Switches the x and y coordinates */
    def transpose = Pos(y, x)
    def tuple = (x, y)
    
    def +(p: Pos) = Pos(x + p.x, y + p.y)
    def -(p: Pos) = Pos(x - p.x, y - p.y)

    def distance(p: Pos) = math.sqrt(math.pow((x - p.x), 2) + math.pow((y - p.y), 2))
    def manhattan(p: Pos) = math.abs(x - p.x) + math.abs(y - p.y)

  object Pos:
    def apply(tup: Pos2D): Pos = Pos(tup._1, tup._2)

  /** Represents a position in 3D space */
  @deprecated("Use (Int, Int, Int) / Vec3 instead", "0.2")
  case class Pos3D(x: Int, y: Int, z: Int):
    def tuple = (x, y, z)
    def toVector = Vector(x, y, z)
    
    def +(p: Pos3D) = Pos3D(x + p.x, y + p.y, z + p.z)
    def -(p: Pos3D) = Pos3D(x - p.x, y - p.y, z - p.z)
    
    private def diff(p: Pos3D) = toVector.zipWith(p.toVector)(_ - _)

    def distance(p: Pos3D) = 
      diff(p).toVector.magnitude
    def manhattan(p: Pos3D) = 
      diff(p).map(math.abs).sum
  
  object Pos3D:
    def apply(tup: (Int, Int, Int)): Pos3D = Pos3D(tup._1, tup._2, tup._3)

  case class Line(start: Pos, end: Pos)
  case class Line3D(start: Pos3D, end: Pos3D)

  /** A simple wrapper class that includes the result of an evaluation and the time (in seconds) it took to evaluate it
   * @param result The final evaluation
   * @param time Time elapsed while evaluating, in seconds
   */
  case class TimedEval[A](duration: Double, result: A)
  object TimedEval:
    /** Times the evaluation of a block of code */
    def time[A](block: => A): TimedEval[A] =
      val start = System.nanoTime()
      val result = block
      val duration = (System.nanoTime() - start) / 1E9
      TimedEval(duration, result)

    def logTime[A](block: => A) = time(block).logAttr(_.duration).result