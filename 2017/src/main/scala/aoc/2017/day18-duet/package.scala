package aoc.y2017
import aoc.utils.*
import collection.mutable as mut

package object day18:
  def extract(data: Seq[String]) = 
    def defined(r: String) = if r.toLongOption.isDefined then None else Some(r)
    data.flatMap {
      case s"$op $r $v" => defined(r)
      case s"$op $r"    => defined(r)
    }.map(_ -> 0L).to(collection.mutable.Map)

  def get(r: String)(using regs: mut.Map[String, Long]) = 
    if r.toLongOption.isDefined then r.toLong else regs(r)
