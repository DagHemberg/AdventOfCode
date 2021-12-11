package aoc.utils

import math.Numeric.Implicits.infixNumericOps

extension [A](a: A)
  def debug = 
    println(a)
    a

// will these be useful?
// probably not but they're fun to do
extension [A: Numeric](xs: Vector[A])
  infix def dot (ys: Vector[A]) = 
    require(xs.size == ys.size, "Vectors must be the same size")
    xs.zip(ys).map((x, y) => x * y).sum

  infix def cross (ys: Vector[A]) = 
    require(xs.size == 3 && ys.size == 3, "Cross product only defined for 3D vectors")
    Vector(
      xs(1) * ys(2) - xs(2) * ys(1), 
      xs(2) * ys(0) - xs(0) * ys(2), 
      xs(0) * ys(1) - xs(1) * ys(0)
    )

  def len = math.sqrt((xs dot xs).toDouble)
  def normalized = xs.map(_.toDouble * (1.0 / xs.len))