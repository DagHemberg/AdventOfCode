package aoc.day25
import aoc.utils.*

object Problem1 extends Solver("25", 58):
  def name = "Sea Cucumber - Part 1"
  
  enum Cucumber(dir: String):
    override def toString = dir
    case Right extends Cucumber(">")
    case Down  extends Cucumber("v")
    case Empty extends Cucumber(".")

  object Cucumber:
    def from(dir: String): Cucumber = dir match
      case ">" => Right
      case "v" => Down
      case "." => Empty

  import Cucumber.*

  extension (p: Index)
    def %(s: (Int, Int)) =
      Index(math.floorMod(p.row, s._1), math.floorMod(p.col, s._2))

  extension (cucumbers: Matrix[Cucumber])
    def stepRight =
      cucumbers zip cucumbers.indices map ((cucumber, i) =>
        cucumber match
          case Empty if cucumbers(i.left % cucumbers.size)  == Right => Right
          case Right if cucumbers(i.right % cucumbers.size) == Empty => Empty
          case _                                                     => cucumber
      )

    def stepDown =
      cucumbers zip cucumbers.indices map ((cucumber, i) =>
        cucumber match
          case Empty if cucumbers(i.up % cucumbers.size)   == Down  => Down
          case Down  if cucumbers(i.down % cucumbers.size) == Empty => Empty
          case _                                                    => cucumber
      )

    def step = cucumbers.stepRight.stepDown

  def solve(data: Vector[String]) =
    var cucumbers = data.map(_.split("").toVector).toMatrix.map(Cucumber.from)
    var steps = 1

    while cucumbers != cucumbers.step do
      cucumbers = cucumbers.step
      steps += 1

    steps
