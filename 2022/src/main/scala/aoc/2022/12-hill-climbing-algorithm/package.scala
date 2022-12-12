package aoc.y2022.day12
import problemutils.*, extensions.*

def parse(data: List[String]) = Matrix.from(data.map(_.toSeq)).zipWithIndex

def makeGraph(init: Matrix[(Char, Pos2D)]) = 
  given mat: Matrix[(Int, Pos2D)] = init.map { 
    case ('S', i) => (1, i)
    case ('E', i) => (26, i)
    case (c, i) => (c.toInt - 96, i)
  }
  
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
