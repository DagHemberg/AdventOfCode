package aoc.y2022.day24
import paut.aoc.*
import problemutils.*, extensions.*
import classes.graphs.*

object Part2 extends Problem(24, 2022)(2)(54):
  def name = "Blizzard Basin - Part 2"
  def solve(data: List[String]) = 
    import Cardinal.*
    
    val init = parse(data)
      .map {
        case '^' => Some(North)
        case 'v' => Some(South)
        case '<' => Some(West)
        case '>' => Some(East)
        case _   => None
      }.zipWithIndex
      .toVector
      .collect:
        case (Some(dir), idx) => Blizzard(dir, idx)
      .toSet

    given dims: Vec2 = parse(data).dimensions
    given Matrix[Pos2D] = parse(data).indices

    val bz = collection.mutable.ListBuffer(init, init.map(_.move))
    while bz.last != bz.head do bz += bz.last.map(_.move)
    bz.dropRightInPlace(1)

    given Vector[Set[Blizzard]] = bz.toVector
    given Int = bz.size

    def generator(pos: Basin) = pos.fromHere.map(p => Edge(pos, p))

    val startingBasin = Basin((-1, 0), 0)
    val stop = dims - (1, 1)

    Graph
      .generate((b: Basin) => b.fromHere)
      .pathFromUntil(startingBasin, _.player == stop, _.player manhattan stop)(using generator)
      .map(_.cost.toInt + 1)
      .getOrElse(-1)
