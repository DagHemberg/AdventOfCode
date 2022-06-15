package aoc.y2017.day07
import aoc.utils.*

object Part2 extends Problem("2017", "07", "2")(60):
  def name = "Recursive Circus - Part 2"
  def solve(data: Seq[String]) = 
    val root = getRoot(data)

    val structured = data.collect {
      case s"$name ($weight) -> $children" => name -> (weight.toInt, children.split(", ").toList)
      case s"$name ($weight)" => name -> (weight.toInt, Nil)
    }.toMap

    case class Program(name: String, weight: Int, children: List[Program])

    def parse(id: String): Program = structured(id) match
      case (weight, Nil) => Program(id, weight, Nil)
      case (weight, children) => Program(id, weight, children.map(parse))

    val tree = parse(root)

    def sumWeights(prog: Program): Int = prog.weight + prog.children.map(sumWeights).sum
    def isBalanced(prog: Program) =
      prog.children.isEmpty || prog.children.map(sumWeights).distinct.size == 1

    def deepestUnbalanced(prog: Program): Option[Program] =
      if isBalanced(prog) then None
      else if prog.children.forall(isBalanced) then Some(prog)
      else prog.children.flatMap(deepestUnbalanced).headOption

    val unbalancedRoot = deepestUnbalanced(tree).get

    val wrongWeight = unbalancedRoot
      .children
      .find(c => sumWeights(c) != sumWeights(unbalancedRoot))
      .get
      .weight
    
    val m = unbalancedRoot
      .children
      .map(sumWeights)
      .groupBy(identity)
      .map((a, b) => a -> b.size)
    
    val unique = m.minBy(_._2)._1
    val other = m.maxBy(_._2)._1
    
    wrongWeight - (unique - other)