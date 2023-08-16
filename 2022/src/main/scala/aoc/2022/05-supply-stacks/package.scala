package aoc.y2022.day05
import problemutils.*, extensions.*

type Crates = Seq[String]
type Stacks = Vector[Crates]
case class Move(amount: Int, from: Int, to: Int)

def parseStacks(data: List[String]) = data
  .split("").head.init
  .map: 
    _.grouped(4)
    .map(_.trim())
    .toList
  .transpose
  .map(_.collect { case s"[$crate]" => crate } )
  .toVector

def parseInstructions(data: List[String]) = data
  .split("").last
  .map:
    case s"move $amount from $origin to $dest" => 
      Move(amount.toInt, origin.toInt - 1, dest.toInt - 1) 
  .toList

def update(stacks: Stacks, move: Move)(using pickupStrat: Crates => Crates) = 
  val newOrigin = stacks(move.from).drop(move.amount)
  val newDest = stacks(move.from).take(move.amount).pipe(pickupStrat) ++ stacks(move.to)
  stacks
    .updated(move.to, newDest)
    .updated(move.from, newOrigin)

def moveCrates(using stacks: Stacks, instructions: List[Move], pickupStrat: Crates => Crates) = 
  instructions
    .foldLeft(stacks)(update)
    .map(_.head)
    .mkString
