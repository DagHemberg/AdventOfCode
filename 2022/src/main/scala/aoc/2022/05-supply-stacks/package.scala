package aoc.y2022.day05
import problemutils.*, extensions.*

type Crates = Seq[String]
type Stacks = Vector[Crates]
case class Move(amount: Int, from: Int, to: Int)

def parseStacks(data: List[String]) = data
  .split("").head.init
  .map { _
    .sliding(4, 4)
    .map(_.trim())
    .toList
  }
  .transpose
  .map(_.collect { case s"[$crate]" => crate } )
  .toVector

def parseInstructions(data: List[String]) = data
  .split("").last
  .map { case s"move $amount from $origin to $dest" => 
    Move(amount.toInt, origin.toInt - 1, dest.toInt - 1) 
  }
  .toList

def moveCrates(strat: Crates => Crates)(using initStacks: Stacks, instructions: List[Move]) = 
  instructions
    .foldLeft(initStacks) { (stacks, mv) => 
      val newOrigin = stacks(mv.from).drop(mv.amount)
      val newDest = stacks(mv.from).take(mv.amount).pipe(strat) ++ stacks(mv.to)

      stacks
        .updated(mv.to, newDest)
        .updated(mv.from, newOrigin)
    }
    .map(_.head)
    .mkString
