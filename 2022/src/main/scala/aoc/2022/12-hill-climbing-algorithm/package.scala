package aoc.y2022.day12
import problemutils.*, extensions.*

def parse(data: List[String]) = Matrix.from(data.map(_.toSeq))

def makeGraph(init: Matrix[Char]) = 
  given mat: Matrix[(Int, Pos2D)] = init.map:
    case 'S' => 1
    case 'E' => 26
    case c => c.toInt - 96
  .zipWithIndex
  
  val edges = mat
    .toVector
    .flatMap { (elevF, from) => from
      .neighboursOrthIn
      .flatMap { (elevT, to) =>
        if elevF >= elevT - 1
        then Some(from ->> to)
        else None
      }
    }

  Graph.from(edges)
