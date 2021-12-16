package aoc
// import com.github.dwickern.macros.NameOf.*
import math.Numeric.Implicits.infixNumericOps

package object utils:

  // extensions
  extension [A](a: A)
    def debug = 
      println(s"[${Console.YELLOW}*${Console.RESET}] $a") // add nameof when thats available
      a
    
    def log = 
      println(s"[${Console.CYAN}*${Console.RESET}] $a")
      a

    def warn(f: A => Boolean) = 
      if f(a) then println(s"[${Console.RED}!${Console.RESET}] $a")
      a

    def time = 
      val start = System.currentTimeMillis()
      val result = a
      val end = System.currentTimeMillis()
      println(s"[${Console.GREEN}+${Console.RESET}] $a took ${end - start}ms")
      result

  extension [A: Numeric](xs: Vector[A])
    def average = xs.sum.toDouble / xs.size
    def middle = xs.sorted.apply(xs.size / 2)

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

    def len = math.sqrt((xs dot xs).toDouble)
    def normalized = xs.map(_.toDouble * (1.0 / xs.len))

  // small case classes
  case class Pos(x: Int, y: Int):
    def transpose = Pos(y, x)
    def tuple = (x, y)
    def +(that: Pos) = Pos(x + that.x, y + that.y)
    def -(that: Pos) = Pos(x - that.x, y - that.y)
    
  case class Line(start: Pos, end: Pos)

  case class TimedEval[A](duration: Double, result: A)
  object TimedEval:
    def time[A](block: => A): TimedEval[A] =
      val start = System.nanoTime()
      val result = block
      val duration = (System.nanoTime() - start) / 1E9
      TimedEval(duration, result)