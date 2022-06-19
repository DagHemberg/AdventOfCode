package aoc.y2017
import aoc.utils.*

package object day16:
  val programs = ('a' to 'p').mkString
  val inds = programs.indices.toList
  
  def parse(data: Seq[String]) = data.head.split(",").toList

  extension (str: String)
    def spin(n: Int) = str.takeRight(n) ++ str.dropRight(n)
    def partner(a: Char, b: Char) =
      str.exchange(str.indexOf(a), str.indexOf(b))
    def exchange(i: Int, j: Int) =
      val (a, b) = str(i) -> str(j)
      str.updated(i, b).updated(j, a)

    def dance(using inst: Seq[String]) = inst.foldLeft(str) { (programs, instruction) =>
      instruction match
        case s"s$n"    => programs.spin(n.toInt)
        case s"x$a/$b" => programs.exchange(a.toInt, b.toInt)
        case s"p$a/$b" => programs.partner(a.head, b.head)
    }
