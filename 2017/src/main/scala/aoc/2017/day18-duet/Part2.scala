package aoc.y2017.day18
import aoc.utils.*
import collection.mutable as mutable

object Part2 extends Problem("2017", "18", "2")(1):
  def name = "Duet - Part 2"
  def solve(data: Seq[String]) = 
    def opposite(char: Char) = char match
      case 'A' => 'B'
      case 'B' => 'A'

    val buffer = Map('A' -> mutable.Queue.empty[Long], 'B' -> mutable.Queue.empty[Long])
    val registry = Map('A' -> extract(data), 'B' -> extract(data))
    val counter = mutable.Map('A' -> 0, 'B' -> 0)
    val pc = mutable.Map('A' -> 0L, 'B' -> 0L)

    def unpause(machine: Char): Unit = 
      given mutable.Map[String, Long] = registry(machine)
      var paused = false
      val other = opposite(machine)

      while !paused && pc(machine) >= 0 && pc(machine) < data.size do
        data(pc(machine).toInt) match
          case s"jgz $r $v" => 
            if get(r) > 0 
            then pc(machine) += get(v) 
            else pc(machine) += 1
          case s"rcv $r" => 
            if buffer(other).isEmpty then 
              paused = true
              if buffer(machine).nonEmpty then unpause(other)
            else 
              pc(machine) += 1
              registry(machine)(r) = buffer(other).dequeue
          case str => 
            pc(machine) += 1
            str match
              case s"snd $r" => 
                counter(machine) += 1
                buffer(machine).enqueue(get(r))
              case s"set $r $v" => registry(machine)(r) = get(v)
              case s"add $r $v" => registry(machine)(r) += get(v)
              case s"mul $r $v" => registry(machine)(r) *= get(v)
              case s"mod $r $v" => registry(machine)(r) %= get(v)

    registry('B')("p") = 1
    unpause('A')
    counter('B')