package aoc.y2022.day21
import problemutils.*, extensions.*

trait Expr:
  val hasUnknown: Boolean
  def eval: Long

  def +(rhs: Expr) = Add(this, rhs)
  def -(rhs: Expr) = Sub(this, rhs)
  def *(rhs: Expr) = Mul(this, rhs)
  def /(rhs: Expr) = Div(this, rhs)
  def ==(rhs: Expr) = Eq(this, rhs)
  def solve: Expr = this match
    case Unknown == expr => Const(expr.eval)
    case left == right if right.hasUnknown => (right == left).solve
    case left == right if left.hasUnknown => 
      val expr = left match
        case a + b => if a.hasUnknown then a == (right - b) else b == (right - a)
        case a - b => if a.hasUnknown then a == (right + b) else b == (a - right)
        case a / b => if a.hasUnknown then a == (b * right) else b == (a / right)
        case a * b => if a.hasUnknown then a == (right / b) else b == (right / a)
      expr.solve
    case other => other

case class Const(value: Long) extends Expr:
  val hasUnknown = false
  def eval = value
  def parse(str: String) = Const(str.toLong)
  override def toString = value.toString

trait Op(op: (Expr, Expr) => Long, str: String) extends Expr:
  val left: Expr
  val right: Expr
  val hasUnknown = left.hasUnknown || right.hasUnknown
  def eval = op(left, right)
  override def toString = s"($left $str $right)"

case class Add(left: Expr, right: Expr) extends Op((a, b) => a.eval + b.eval, "+")
case class Mul(left: Expr, right: Expr) extends Op((a, b) => a.eval * b.eval, "*")
case class Div(left: Expr, right: Expr) extends Op((a, b) => a.eval / b.eval, "/")
case class Sub(left: Expr, right: Expr) extends Op((a, b) => a.eval - b.eval, "-")
case class Eq(left: Expr, right: Expr) extends Op((a, b) => (a.eval == b.eval).toInt, "==")

case object Unknown extends Expr:
  def eval = throw Exception("Encountered unknown value")
  val hasUnknown = true

trait Extractor[A <: Op]:
  def unapply(arg: A) = (arg.left, arg.right)

object * extends Extractor[Mul]
object + extends Extractor[Add]
object / extends Extractor[Div]
object - extends Extractor[Sub]
object == extends Extractor[Eq]
object Op extends Extractor[Op]

def build(str: String)(using lookup: Map[String, String], part2: Boolean = false): Expr = 
  str match
    case value if value.toLongOption.isDefined => Const(value.toLong)
    case s"$left + $right" => build(left) + build(right)
    case s"$left - $right" => build(left) - build(right)
    case s"$left * $right" => build(left) * build(right)
    case s"$left / $right" => build(left) / build(right)
    case "humn" if part2 => Unknown
    case "root" if part2 => build(lookup("root")) match
      case Op(a, b) => a == b
    case name => build(lookup(name))

def parse(data: List[String]) = data.map {
  case s"$name: $expr" => name -> expr
}.toMap
