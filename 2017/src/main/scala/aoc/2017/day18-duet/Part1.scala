package aoc.y2017.day18
import aoc.utils.*

object Part1 extends Problem("2017", "18", "1")(4L):
  def name = "Duet - Part 1"
  def solve(data: Seq[String]) = 
    given regs: collection.mutable.Map[String, Long] = extract(data)

    var i = 0L
    var found = false
    var latest = 0L

    while !found && i >= 0 && i < data.size do
      data(i.toInt) match
        case s"jgz $r $v" => 
          if regs(r) > 0 
          then i += get(v) 
          else i += 1
        case str => 
          str match
            case s"snd $r"    => latest = regs(r)
            case s"set $r $v" => regs(r) = get(v)
            case s"add $r $v" => regs(r) += get(v)
            case s"mul $r $v" => regs(r) *= get(v)
            case s"mod $r $v" => regs(r) %= get(v)
            case s"rcv $r" => 
              if regs(r) != 0 
              then 
                found = true 
                regs(r) = latest
          i += 1
          

    latest
